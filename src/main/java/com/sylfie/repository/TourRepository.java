package com.sylfie.repository;

import com.sylfie.model.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {
    List<Tour> findAll();
}
