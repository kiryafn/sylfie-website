package com.sylfie.model.entity;

import com.sylfie.exception.InsufficientBalanceException;
import com.sylfie.model.dto.UserRegisterDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String passwordHash;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 16)
    private String phoneNumber;

    @Column(length = 50)
    private String address;

    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal bonusBalance;

    private LocalDateTime createdAt;

    public User(String username, String email, String passwordHash, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) {
        this.username      = username;
        this.email         = email;
        this.passwordHash  = passwordHash;
        this.firstName     = firstName;
        this.lastName      = lastName;
        this.phoneNumber   = phoneNumber;
        this.address       = address;
        this.dateOfBirth   = dateOfBirth;
        this.balance       = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.bonusBalance  = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.createdAt = LocalDateTime.now();
    }

    public User(UserRegisterDTO userRegisterDTO) {
        this.username      = userRegisterDTO.getUsername();
        this.email         = userRegisterDTO.getEmail();
        this.passwordHash  = userRegisterDTO.getPassword();
        this.firstName     = userRegisterDTO.getFirstName();
        this.lastName      = userRegisterDTO.getLastName();
        this.phoneNumber   = userRegisterDTO.getPhoneNumber();
        this.address       = userRegisterDTO.getAddress();
        this.dateOfBirth   = userRegisterDTO.getDateOfBirth();
        this.balance       = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.bonusBalance  = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.createdAt = LocalDateTime.now();

    }

    public User() {}

    public void credit(BigDecimal amount) {
        requirePositive(amount);
        this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void debit(BigDecimal amount) {
        requirePositive(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient funds on balance");
        }
        this.balance = this.balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private static void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    String.format("Amount must be positive, but was %s", amount)
            );
        }
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(r -> r.getName().equals(roleName));
    }


    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(BigDecimal bonusBalance) {
        this.bonusBalance = bonusBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        if (avatar != null && avatar.getUser() != this) {
            avatar.setUser(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }
}