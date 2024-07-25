package com.project.shopapp.mapper;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.response.UserResponse;
import com.project.shopapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);

    @Mapping(target = "roleId", source = "role.id")
    UserResponse toUserResponse(User user);


}
