package com.sylfie.mapper;

import com.sylfie.model.dto.UserRegisterDTO;
import com.sylfie.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "password", target = "passwordHash")
    User toUser(UserRegisterDTO userRegisterDTO);
}
