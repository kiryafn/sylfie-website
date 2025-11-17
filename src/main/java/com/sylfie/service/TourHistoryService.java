package com.sylfie.service;

import com.sylfie.dto.tour.history.TourHistoryResponseDto;
import com.sylfie.dto.tour.tour.TourResponseDto;
import com.sylfie.mapper.TourHistoryMapper;
import com.sylfie.model.Status;
import com.sylfie.model.UserTourHistory;
import com.sylfie.repository.TourHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourHistoryService {

    private final TourHistoryRepository tourHistoryRepository;
    private final TourHistoryMapper tourMapper;

    public TourHistoryService(TourHistoryRepository tourHistoryRepository, TourHistoryMapper tourMapper) {
        this.tourHistoryRepository = tourHistoryRepository;
        this.tourMapper = tourMapper;
    }

    public List<UserTourHistory> getAll() {
        return tourHistoryRepository.findAll();
    }

    public List<UserTourHistory> getByUserId(Long userId) {
        return tourHistoryRepository.findAllByUserId(userId);
    }

    public UserTourHistory getById(Long id) {
        return tourHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserTourHistory not found with id: " + id));
    }

    public Page<TourHistoryResponseDto> getPage(Pageable pageable) {
        return tourHistoryRepository.findAll(pageable)
                .map(tourMapper::toResponseDto);
    }

    @Transactional
    public UserTourHistory create(UserTourHistory userTourHistory) {
        return tourHistoryRepository.save(userTourHistory);
    }

    public List<Long> findTopTourTemplateIds(org.springframework.data.domain.Pageable pageable) {
        return tourHistoryRepository.findTopTourTemplateIds(pageable);
    }

    @Transactional
    public UserTourHistory update(UserTourHistory userTourHistory) {
        return tourHistoryRepository.save(userTourHistory);
    }

    @Transactional
    public void delete(Long id) {
        UserTourHistory existing = getById(id);
        tourHistoryRepository.delete(existing);
    }

    //TODO: TO DTO
    @Transactional
    public List<TourHistoryResponseDto> getByUserName(String username) {
        return tourHistoryRepository.findAllByUserUsername(username).stream()
                .map(history -> new TourHistoryResponseDto(
                        history.getTour().getTemplate().getName(),
                        history.getBookingDate(),
                        history.getPriceAtBooking(),
                        history.getStatus().getName()
                ))
                .toList();
    }

    @Transactional
    public void markExpiredToursAsCompleted() {
        List<UserTourHistory> activeBookings = tourHistoryRepository.findAllByStatus(Status.BOOKED);

        LocalDateTime now = LocalDateTime.now();

        for (UserTourHistory booking : activeBookings) {
            if (booking.getTour().getEndDate().isBefore(now)) {
                booking.setStatus(Status.COMPLETED);
            }
        }

        tourHistoryRepository.saveAll(activeBookings);
    }
}