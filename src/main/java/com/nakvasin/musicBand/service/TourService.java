package com.nakvasin.musicBand.service;

import com.nakvasin.musicBand.dto.TourDTO;
import com.nakvasin.musicBand.entity.Group;
import com.nakvasin.musicBand.repository.GroupRepository;
import com.nakvasin.musicBand.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.temporal.ChronoUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final GroupRepository groupRepository;

    public Mono<ServerResponse> getLocationAndDuration(ServerRequest serverRequest) {
        String groupId = serverRequest.pathVariable("groupId");
        Flux<TourDTO> tourLocationAndDuration = tourRepository.findAll()
                .filter(tour -> tour.getGroupId().equals(groupId))
                .publishOn(Schedulers.boundedElastic())
                .map(tour -> TourDTO.builder()
                        .groupName(groupRepository.findById(groupId)
                                .map(Group::getName)
                                .block())
                        .location(tour.getLocation())
                        .duration((int) tour.getStartDate().until(tour.getEndDate(), ChronoUnit.DAYS))
                        .build());
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(tourLocationAndDuration, TourDTO.class);
    }

    public Mono<ServerResponse> getTicketPrice(ServerRequest serverRequest) {
        String groupId = serverRequest.pathVariable("groupId");

        Flux<TourDTO> groupNameAndTicketPrice = tourRepository.findAll()
                .filter(tour -> tour.getGroupId().equals(groupId))
                .publishOn(Schedulers.boundedElastic())
                .map(tour -> TourDTO.builder()
                        .groupName(groupRepository.findById(groupId)
                                .map(Group::getName)
                                .block())
                        .ticketPrice(tour.getTicketPrice())
                        .build());
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(groupNameAndTicketPrice, TourDTO.class);
    }
}
