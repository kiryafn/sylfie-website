package com.sylfie.service;

import com.sylfie.exception.MaximumGroupSizeExcededException;
import com.sylfie.model.Status;
import com.sylfie.model.Tour;
import com.sylfie.model.User;
import com.sylfie.model.UserTourHistory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class BookingService {

    private final TourService tourService;
    private final UserService userService;
    private final TourHistoryService historyService;

    public BookingService(TourService tourService, UserService userService, TourHistoryService historyService) {

        this.tourService = tourService;
        this.userService = userService;
        this.historyService = historyService;
    }

    public void bookTour(Long tourId , String username) {
        Tour tour = tourService.getById(tourId);

        if (tour.getBookedCount() >= tour.getTemplate().getMaxParticipants()){
            throw new MaximumGroupSizeExcededException("Tour is full");
        }

        User user = userService.getByUsername(username);

        BigDecimal price = tour.getTemplate().getPrice();

        userService.debitBalance(user, price);

        BigDecimal bonus = price.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        user.setBonusBalance(user.getBonusBalance().add(bonus));

        UserTourHistory history = new UserTourHistory(user, tour, Status.BOOKED);
        history.setPriceAtBooking(price);
        historyService.create(history);

        tour.setBookedCount(tour.getBookedCount() + 1);
    }
}