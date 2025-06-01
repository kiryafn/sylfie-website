package com.sylfie.repository;

import com.sylfie.model.TourCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourCategoryRepository extends CrudRepository<TourCategory, Long> {
    List<TourCategory> findAll();
}
