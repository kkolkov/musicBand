package com.nakvasin.musicBand.controller;

import com.nakvasin.musicBand.service.TourReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@RequiredArgsConstructor
public class ReportController {
    private final TourReportService tourReportService;
    @Bean
    public RouterFunction<?> reportRoutes() {
        return route(GET("/tour/getTourReport/"), tourReportService::getTourReport);
    }
}
