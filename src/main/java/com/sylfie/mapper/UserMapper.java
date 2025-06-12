package com.sylfie.mapper;

import com.sylfie.dto.user.UserResponseDto;
import com.sylfie.dto.auth.RegisterDto;
import com.sylfie.model.User;
import com.sylfie.security.OAuth2UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(RegisterDto ur){
        User user = new User();
        user.setUsername(ur.getUsername());
        user.setEmail(ur.getEmail());
        user.setPassword(ur.getPassword());
        user.setFirstName(ur.getFirstName());
        user.setLastName(ur.getLastName());
        user.setPhoneNumber(ur.getPhoneNumber());
        user.setDateOfBirth(ur.getDateOfBirth());
        return user;
    }

    public User toUser(OAuth2UserInfo oa2ui){
        User user = new User();
        user.setUsername(oa2ui.getUsername());
        user.setEmail(oa2ui.getEmail());
        user.setPassword(null);
        user.setFirstName(oa2ui.getFirstName());
        user.setLastName(oa2ui.getLastName());
        user.setProvider(oa2ui.getProvider());

        return user;
    }

    public UserResponseDto toInfoDTO(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setBalance(user.getBalance());
        dto.setBonusBalance(user.getBonusBalance());
        dto.setAvatarUrl(user.getAvatar().getPicture().getUrl());
        return dto;
    }

    public User toUser(UserResponseDto dto, User user){
        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDateOfBirth() != null) user.setDateOfBirth(dto.getDateOfBirth());
        return user;
    }
}
