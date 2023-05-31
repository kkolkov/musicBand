package com.nakvasin.musicBand.controller;

import com.nakvasin.musicBand.service.SongService;
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
public class SongController {
    private final SongService songService;
    @Bean
    public RouterFunction<?> songRoutes() {
        return route(GET("/songs/getTopInChartRepertoire").and(accept(MediaType.APPLICATION_JSON)), songService::getTopInChartRepertoire)
                .andRoute(GET("/songs/getLyricistComposerAndDateCreated/{name}").and(accept(MediaType.APPLICATION_JSON)), songService::getLyricistComposerAndDateCreated);
    }
}
