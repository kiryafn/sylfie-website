package com.sylfie.scheduler;

import com.sylfie.service.TourHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TourHistoryStatusUpdater {

    private final TourHistoryService tourHistoryService;

    public TourHistoryStatusUpdater(TourHistoryService tourHistoryService) {
        this.tourHistoryService = tourHistoryService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void updateCompletedTours() {
        tourHistoryService.markExpiredToursAsCompleted();
    }
}