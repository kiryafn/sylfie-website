package com.sylfie.repository;

import com.sylfie.model.entity.TourCategory;
import com.sylfie.model.entity.TourTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourTemplateRepository extends CrudRepository<TourTemplate, Long> {
    List<TourTemplate> findAll();
    List<TourTemplate> findAllById(Iterable<Long> ids);
    Optional<TourTemplate> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
