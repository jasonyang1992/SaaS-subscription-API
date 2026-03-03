package com.saas.mappers;

import com.saas.model.DTO.UserDTO;
import com.saas.model.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
