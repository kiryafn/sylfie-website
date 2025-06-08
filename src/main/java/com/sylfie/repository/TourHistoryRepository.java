package com.sylfie.repository;


import com.sylfie.model.UserTourHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TourHistoryRepository extends CrudRepository<UserTourHistory, Long> {
    List<UserTourHistory> findAllByUserId(Long userId);
    List<UserTourHistory> findAll();

    @Query("select th.tour.id from UserTourHistory th " +
            "group by th.tour.id " +
            "order by count(th) desc")
    List<Long> findTopTourIds(Pageable pageable);

}
