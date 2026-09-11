package com.ecommerce.web;

import com.ecommerce.processing.AnalyticsIngestionService;

public class OrderController {
    private final AnalyticsIngestionService analyticsService;

    public OrderController(AnalyticsIngestionService analyticsService) {
        this.analyticsService = analyticsService;
    }

    public void createOrder() {
        analyticsService.trackEvent("ORDER_CREATED");
    }
}