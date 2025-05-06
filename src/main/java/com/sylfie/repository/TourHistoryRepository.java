package com.sylfie.repository;


import com.sylfie.model.entity.TourHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TourHistoryRepository extends CrudRepository<TourHistory, Long> {
    List<TourHistory> findAllByUserId(Long userId);
    List<TourHistory> findAll();

    @Query("select th.tour.id from TourHistory th " +
            "group by th.tour.id " +
            "order by count(th) desc")
    List<Long> findTopTourIds(Pageable pageable);

}
