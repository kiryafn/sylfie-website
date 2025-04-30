package com.sylfie.service;

import com.sylfie.model.entity.Review;
import com.sylfie.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository rewiewRepository;

    public ReviewService(ReviewRepository rewiewRepository) {
        this.rewiewRepository = rewiewRepository;
    }

    public List<Review> getAll() {
        return rewiewRepository.findAll();
    }

    public Review getById(Long id) {
        return rewiewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    @Transactional
    public Review create(Review review) {
        return rewiewRepository.save(review);
    }

    @Transactional
    public Review update(Review review) {
        return rewiewRepository.save(review);
    }

    @Transactional
    public void delete(Long id) {
        Review review = getById(id);
        rewiewRepository.delete(review);
    }
}