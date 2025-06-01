package com.sylfie.service;

import com.sylfie.model.Review;
import com.sylfie.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    @Transactional
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public void delete(Long id) {
        Review review = getById(id);
        reviewRepository.delete(review);
    }
}