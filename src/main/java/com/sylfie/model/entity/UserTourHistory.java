package com.sylfie.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_tour_history")
public class UserTourHistory {

    /**
     * Здесь нет отдельного генератора ID — мы берём его из Reservation.id
     */
    @Id
    @Column(name = "reservation_id")
    private Long id;

    /**
     * One-to-One связь на ту же запись Reservation.
     * @MapsId гарантирует, что PK этого объекта = reservation.id
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    /** Процент (или другую метрику) завершённости тура */
    @Column(nullable = false)
    private Integer completion;

    /** Текущий статус (например, IN_PROGRESS, COMPLETED и т.п.) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserTourStatus status;

    /** Когда статус был обновлён */
    @Column(name = "status_updated_at", nullable = false)
    private LocalDateTime statusUpdatedAt;

    /** Когда тур фактически завершён (nullable) */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // --- конструкторы ---

    public UserTourHistory() {}

    public UserTourHistory(Integer completion,
                           UserTourStatus status,
                           LocalDateTime statusUpdatedAt,
                           LocalDateTime completedAt) {
        this.completion      = completion;
        this.status          = status;
        this.statusUpdatedAt = statusUpdatedAt;
        this.completedAt     = completedAt;
    }

    // --- геттеры / сеттеры ---

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        this.id = reservation.getId();
    }

    public Integer getCompletion() {
        return completion;
    }
    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public UserTourStatus getStatus() {
        return status;
    }
    public void setStatus(UserTourStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }
    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}