package com.project.shopapp.mapper;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.response.UserResponse;
import com.project.shopapp.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    UserResponse toUserResponse(User user);


}
