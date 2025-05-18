package com.sylfie.mapper;

import com.sylfie.model.dto.UserRegisterDTO;
import com.sylfie.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRegisterDTO ur){
        return new User(
                ur.getUsername(),
                ur.getEmail(),
                ur.getPassword(),
                ur.getFirstName(),
                ur.getLastName(),
                ur.getPhoneNumber(),
                ur.getDateOfBirth());
    }
}
