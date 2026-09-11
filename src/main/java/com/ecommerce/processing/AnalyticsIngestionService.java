package com.ecommerce.processing;


import com.ecommerce.repository.analytics.ClickHouseRepository;

public class AnalyticsIngestionService {
    private final ClickHouseRepository analyticsRepository;

    public AnalyticsIngestionService(ClickHouseRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public void trackEvent(String eventType) {
        analyticsRepository.saveEvent(eventType);
    }
}