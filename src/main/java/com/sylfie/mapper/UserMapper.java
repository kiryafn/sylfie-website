package com.sylfie.mapper;

import com.sylfie.dto.UserBalanceDTO;
import com.sylfie.dto.UserProfileDTO;
import com.sylfie.dto.UserRegisterDTO;
import com.sylfie.model.User;
import com.sylfie.security.OAuth2UserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRegisterDTO ur){
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

    public User toUser(UserProfileDTO ur){
        User user = new User();
        user.setUsername(ur.getUsername());
        user.setEmail(ur.getEmail());
        user.setPassword(ur.getPassword());
        user.setFirstName(ur.getFirstName());
        user.setLastName(ur.getLastName());
        user.setPhoneNumber(ur.getPhoneNumber());
        user.setDateOfBirth(ur.getDateOfBirth());
        user.setAvatar(ur.getAvatar());
        return user;
    }

    public UserProfileDTO toProfileDTO(User user){
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAvatar(user.getAvatar());
        return dto;
    }

    public UserBalanceDTO toBalanceDTO(User user){
        UserBalanceDTO dto = new UserBalanceDTO();
        dto.setBalance(user.getBalance());
        dto.setBonusBalance(user.getBonusBalance());

        return dto;
    }
}
