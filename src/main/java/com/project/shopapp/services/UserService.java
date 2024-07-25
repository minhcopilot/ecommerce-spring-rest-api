package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.response.UserResponse;
import com.project.shopapp.mapper.UserMapper;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenUtil jwtTokenUtil;
    AuthenticationManager authenticationManager;
    public UserResponse createUser(UserDTO userDTO) {
        //check existing user
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new RuntimeException("User already exists");
        }
        //map userDTO to user
        User user = userMapper.toUser(userDTO);
        //set role
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        //check if user is not using social media account set password
        if(userDTO.getFacebookAccountId()==0 && userDTO.getGoogleAccountId()==0){
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);

        }
        try {
            user = userRepository.save(user);
        } catch (Exception e){
            throw new RuntimeException("Failed to create user");
        }
        return userMapper.toUserResponse(user);
    }

    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User existedUser = user.get();
        if(existedUser.getFacebookAccountId() ==0 && existedUser.getGoogleAccountId()==0){
            if(!passwordEncoder.matches(password,existedUser.getPassword())){
                throw new RuntimeException("phone number and password is incorrect");
            }
        }
        //custom authentication with phone number and password instead of username va password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber,password);
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existedUser);
    }
}
