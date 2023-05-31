package com.nakvasin.musicBand.controller;

import com.nakvasin.musicBand.service.TourReportService;
import com.nakvasin.musicBand.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TourController {
    private final TourService tourService;
    @Bean
    public RouterFunction<?> tourRoutes() {
        return route(GET("/tour/getLocationAndDuration/{groupId}").and(accept(MediaType.APPLICATION_JSON)), tourService::getLocationAndDuration)
                .andRoute(GET("/tour/getTicketPrice/{groupId}").and(accept(MediaType.APPLICATION_JSON)), tourService::getTicketPrice);
    }
}
