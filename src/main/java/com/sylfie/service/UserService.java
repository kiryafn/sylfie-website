package com.sylfie.service;

import com.sylfie.model.entity.User;
import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id " + id)
                );
    }

    @Transactional
    public User create(User user) {
        // здесь можно добавить логику хеширования пароля, установки ролей по умолчанию и т.п.
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
            // если вы хотите использовать своё исключение
            throw new InsufficientBalanceException(e.getMessage());
        }
        return userRepository.save(user);
    }
}
