package com.sylfie.mapper;

import com.sylfie.dto.auth.MeDto;
import com.sylfie.dto.auth.RegisterDto;
import com.sylfie.model.Role;
import com.sylfie.model.User;
import com.sylfie.security.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public MeDto toInfoDTO(User user){
        return new MeDto(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getBalance(),
                user.getBonusBalance(),
                user.getAvatar().getPicture().getUrl(),
                user.getRoles().stream().map(Role::getName).toList()
        );

    }

    public User toUser(MeDto dto, User user){
        if (dto.username() != null) user.setUsername(dto.username());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        if (dto.phoneNumber() != null) user.setPhoneNumber(dto.phoneNumber());
        if (dto.dateOfBirth() != null) user.setDateOfBirth(dto.dateOfBirth());
        return user;
    }
}
