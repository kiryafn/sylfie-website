package com.sylfie.repository;

import com.sylfie.model.TourTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourTemplateRepository extends CrudRepository<TourTemplate, Long>, PagingAndSortingRepository<TourTemplate, Long> {
    List<TourTemplate> findAllById(Iterable<Long> ids);
    Optional<TourTemplate> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
