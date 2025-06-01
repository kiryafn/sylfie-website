package com.sylfie.service;

import com.sylfie.model.TourHistory;
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

    public List<TourHistory> getAll() {
        return tourHistoryRepository.findAll();
    }

    public List<TourHistory> getByUserId(Long userId) {
        return tourHistoryRepository.findAllByUserId(userId);
    }

    public TourHistory getById(Long id) {
        return tourHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TourHistory not found with id: " + id));
    }

    @Transactional
    public TourHistory create(TourHistory tourHistory) {
        return tourHistoryRepository.save(tourHistory);
    }

    @Transactional
    public TourHistory update(TourHistory tourHistory) {
        return tourHistoryRepository.save(tourHistory);
    }

    @Transactional
    public void delete(Long id) {
        TourHistory existing = getById(id);
        tourHistoryRepository.delete(existing);
    }
}