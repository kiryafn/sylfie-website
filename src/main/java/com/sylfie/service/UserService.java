package com.sylfie.service;

import com.sylfie.exception.EmailTakenException;
import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.exception.UsernameTakenException;
import com.sylfie.mapper.UserMapper;
import com.sylfie.dto.UserRegisterDTO;
import com.sylfie.model.User;
import com.sylfie.model.Role;
import com.sylfie.repository.RoleRepository;
import com.sylfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final static String DEFAULT_ROLE = "USER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }
        return user;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id " + id)
                );
    }

    @Transactional
    public User create(UserRegisterDTO userRegisterDTO) {
        isUserValidToCreate(userRegisterDTO);
        User user = userMapper.toUser(userRegisterDTO);
        user.setBalance(BigDecimal.ZERO);
        user.setBonusBalance(BigDecimal.ZERO);
        user.setPasswordHash(passwordEncoder.encode(userRegisterDTO.getPassword()));
        Role role = roleRepository.findByName(DEFAULT_ROLE);
        user.getRoles().add(role);

        return userRepository.save(user);
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
        User existingUserByEMail = userRepository.findByEmail(ur.getEmail());
        if (existingUserByEMail != null) throw new EmailTakenException("This email is already taken");

        User existingUserByUsername = userRepository.findByUsername(ur.getUsername());
        if (existingUserByUsername != null) throw new UsernameTakenException("This username is already taken");
    }
}