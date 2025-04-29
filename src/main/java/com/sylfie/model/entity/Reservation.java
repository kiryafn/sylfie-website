package com.sylfie.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Ссылка на конкретный тур (экземпляр тура) */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    /** Пользователь, сделавший бронь */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Когда сделана бронь */
    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    /** Цена на момент бронирования */
    @Column(name = "price_at_booking", nullable = false, precision = 19, scale = 2)
    private BigDecimal priceAtBooking;

    /** Статус оплаты */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    /** Метод оплаты (CARD, PAYPAL и т.п.) */
    @Column(name = "payment_method", length = 255)
    private String paymentMethod;

    /**
     * Если бронь завершена, будет ссылка на историю.
     * CascadeType.ALL — чтобы при удалении Reservation убиралась и история.
     */
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserTourHistory history;

    // --- конструкторы, геттеры/сеттеры ---

    public Long getId() { return id; }

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

    public BigDecimal getPriceAtBooking() { return priceAtBooking; }
    public void setPriceAtBooking(BigDecimal priceAtBooking) { this.priceAtBooking = priceAtBooking; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public UserTourHistory getHistory() { return history; }
    public void setHistory(UserTourHistory history) {
        this.history = history;
        history.setReservation(this);
    }
}