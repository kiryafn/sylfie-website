package com.sylfie.security;

import com.sylfie.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username)
                .map(CustomUserDetails::new) // Преобразуем User в CustomUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
