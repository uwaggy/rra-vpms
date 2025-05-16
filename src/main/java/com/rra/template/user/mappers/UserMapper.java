package com.rra.template.user.mappers;

import com.rra.template.auth.dtos.RegisterRequestDTO;
import com.rra.template.user.User;
import com.rra.template.user.dtos.UserResponseDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toEntity(RegisterRequestDTO userDto);
    UserResponseDTO toResponseDTO(User user);
}
//simplifying data transformation in the user registration and response process.