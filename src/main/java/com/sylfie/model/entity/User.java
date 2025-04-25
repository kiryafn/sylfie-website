package com.sylfie.model.entity;

import com.sylfie.exception.InsufficientBalanceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@ToString(exclude = "passwordHash")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "Username can contain letters, numbers, dots, underscores and hyphens only")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password hash is mandatory")
    @Size(min = 60, max = 60, message = "Password hash must be a BCrypt hash of length 60")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Pattern(regexp = "^[\\p{L} '–-]+$", message = "First name contains invalid characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Pattern(regexp = "^[\\p{L} '–-]+$", message = "Last name contains invalid characters")
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be between 7 and 15 digits, optional leading +")
    private String phoneNumber;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull(message = "Balance is mandatory")
    @DecimalMin(value = "0.0", message = "Balance must be non-negative")
    @Column(precision = 19, scale = 2)
    private BigDecimal balance;

    @NotNull(message = "Bonus balance is mandatory")
    @DecimalMin(value = "0.0", message = "Bonus balance must be non-negative")
    @Column(precision = 19, scale = 2)
    private BigDecimal bonusBalance;

    @CreatedDate
    @PastOrPresent(message = "Creation timestamp cannot be in the future")
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @PastOrPresent(message = "Update timestamp cannot be in the future")
    @Column(nullable = false)
    private Instant updatedAt;

    public User(String username, String email, String passwordHash, String firstName, String lastName, String phoneNumber, UserRole role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.bonusBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

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
                    String.format("Amount must be positive, but was %s", amount));
        }
    }
}
