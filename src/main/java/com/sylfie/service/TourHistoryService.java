package com.sylfie.service;

import com.sylfie.dto.mvc.UserTourHistoryDTO;
import com.sylfie.model.UserTourHistory;
import com.sylfie.repository.TourHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourHistoryService {

    private final TourHistoryRepository tourHistoryRepository;

    public TourHistoryService(TourHistoryRepository tourHistoryRepository) {
        this.tourHistoryRepository = tourHistoryRepository;
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

    @Transactional
    public UserTourHistory create(UserTourHistory userTourHistory) {
        return tourHistoryRepository.save(userTourHistory);
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
    public List<UserTourHistoryDTO> getByUserName(String username) {
        return tourHistoryRepository.findAllByUserUsername(username).stream()
                .map(history -> new UserTourHistoryDTO(
                        history.getTour().getTemplate().getName(),
                        history.getBookingDate(),
                        history.getPriceAtBooking(),
                        history.getStatus().getName()
                ))
                .toList();
    }
}