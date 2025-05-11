package com.sylfie.security;

import com.sylfie.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Преобразуем роли пользователя в формат GrantedAuthority для Spring Security
        return user.getRoles().stream()
                .map(role -> (GrantedAuthority) role::getName) // Предполагается, что Role имеет метод getName()
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash(); // Используем hashed пароль
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Если нет других проверок на срок действия аккаунта, просто возвращаем true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // По умолчанию аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Если нет сроков окончания пароля, возвращаем true
    }

    @Override
    public boolean isEnabled() {
        return true; // Если есть, например, поле isActive в User, можно проверять его
    }

    // Дополнительный метод для получения оригинальной сущности User
    public User getUser() {
        return user;
    }
}