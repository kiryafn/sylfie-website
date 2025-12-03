package com.sylfie.repository;

import com.sylfie.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
    List<Review> findAll();
}
