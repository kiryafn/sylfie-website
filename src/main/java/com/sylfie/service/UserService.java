package com.sylfie.service;

import com.sylfie.exception.EmailTakenException;
import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.exception.UsernameTakenException;
import com.sylfie.mapper.UserMapper;
import com.sylfie.dto.UserRegisterDTO;
import com.sylfie.model.Avatar;
import com.sylfie.model.User;
import com.sylfie.repository.UserRepository;
import com.sylfie.security.OAuth2UserInfo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final static String DEFAULT_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final Avatar defaultAvatar;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper, Avatar defaultAvatar) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.defaultAvatar = defaultAvatar;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
        return user;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id " + id)
                );
    }

    @Transactional
    public User createFromDTO(UserRegisterDTO urdto) {
        isUserValidToCreate(urdto);
        User user = userMapper.toUser(urdto);
        user.setPassword(passwordEncoder.encode(urdto.getPassword()));

        user.setBalance(BigDecimal.ZERO);
        user.setBonusBalance(BigDecimal.ZERO);
        user.addRole(roleService.getByName(DEFAULT_ROLE));
        user.setAvatar(defaultAvatar);

        return userRepository.save(user);
    }

    @Transactional
    public User createFromOAuth2(OAuth2UserInfo oa2ui) {
        return userRepository.findByEmail(oa2ui.getEmail())
                .map(user -> {
                    user.setFirstName(oa2ui.getFirstName());
                    user.setLastName(oa2ui.getLastName());
                    user.setProvider(oa2ui.getProvider());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    User user = userMapper.toUser(oa2ui);
                    user.setBalance(BigDecimal.ZERO);
                    user.setBonusBalance(BigDecimal.ZERO);
                    user.addRole(roleService.getByName(DEFAULT_ROLE));
                    user.setAvatar(defaultAvatar);

                    return userRepository.save(user);
                });
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User creditBalance(Long id, BigDecimal amount) {
        User user = getById(id);
        user.credit(amount);
        return userRepository.save(user);
    }

    @Transactional
    public User debitBalance(Long id, BigDecimal amount) {
        User user = getById(id);
        try {
            user.debit(amount);
        } catch (IllegalArgumentException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }
        return userRepository.save(user);
    }

    private void isUserValidToCreate(UserRegisterDTO ur) {
        userRepository.findByEmail(ur.getEmail())
                .ifPresent(user -> { throw new EmailTakenException("Email already taken"); });

        userRepository.findByUsername(ur.getUsername())
                .ifPresent(user -> { throw new UsernameTakenException("Username already taken"); });
    }

    private void isUserValidToCreate(OAuth2UserInfo oa2ui) {
        userRepository.findByEmail(oa2ui.getEmail())
                .ifPresent(user -> { throw new EmailTakenException("Email already taken"); });

        userRepository.findByUsername(oa2ui.getUsername())
                .ifPresent(user -> { throw new UsernameTakenException("Username already taken"); });
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
    }

    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }
}