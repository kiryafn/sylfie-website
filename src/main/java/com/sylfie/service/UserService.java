package com.sylfie.service;

import com.sylfie.dto.user.UserResponseDto;
import com.sylfie.exception.EmailTakenException;
import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.exception.UsernameTakenException;
import com.sylfie.mapper.UserMapper;
import com.sylfie.dto.auth.RegisterDto;
import com.sylfie.model.Avatar;
import com.sylfie.model.Picture;
import com.sylfie.model.TourTemplate;
import com.sylfie.model.User;
import com.sylfie.repository.UserRepository;
import com.sylfie.security.OAuth2UserInfo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final PictureService pictureService;
    private final TourTemplateService tourTemplateService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper, Avatar defaultAvatar, PictureService pictureService, TourTemplateService tourTemplateService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.defaultAvatar = defaultAvatar;
        this.pictureService = pictureService;
        this.tourTemplateService = tourTemplateService;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void addOrRemoveFavourireTour(String username, Long tourId) {
        User user = getByUsername(username);
        TourTemplate tour = tourTemplateService.getById(tourId);

        if (user.isFavourite(tour)) {
            user.removeFavourite(tour);
        } else {
            user.addFavourite(tour);
        }
        userRepository.save(user);
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
    public User createFromDTO(RegisterDto urdto) {
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
    public UserResponseDto update(UserResponseDto dto, String username){
        User user = getByUsername(username);
        userMapper.toUser(dto, user);

        user = userRepository.save(user);
        return userMapper.toInfoDTO(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User creditBalance(User user, BigDecimal amount) {
        user.credit(amount);
        return userRepository.save(user);
    }

    @Transactional
    public User debitBalance(User user, BigDecimal amount) {
        try {
            user.debit(amount);
        } catch (IllegalArgumentException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }
        return userRepository.save(user);
    }

    private void isUserValidToCreate(RegisterDto ur) {
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

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
    }

    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    //TODO: Possibly change this to PasswordDTO
    public void changePassword(String name, String currentPassword, String newPassword, String confirmNewPassword) {
        User user = getByUsername(name);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password does not match");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new IllegalArgumentException("New password does not match");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        System.out.println("Before save: " + user.getPassword());
        userRepository.save(user);
        System.out.println("After save: " + user.getPassword());
    }

    public void updateAvatar(String username, MultipartFile avatar) throws IOException {
        User user = getByUsername(username);
        pictureService.deletePicture(user.getAvatar().getPicture().getId());
        Picture picture = pictureService.saveAvatar(avatar);
        user.setAvatar(new Avatar(picture));
    }

    public List<Long> getFavouriteTourIds(String username) {
        User user = getByUsername(username);
        return user.getFavourites().stream().map(TourTemplate::getId).toList();
    }
}