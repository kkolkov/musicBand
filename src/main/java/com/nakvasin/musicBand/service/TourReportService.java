package com.nakvasin.musicBand.service;

import com.nakvasin.musicBand.report.TourReport;
import com.nakvasin.musicBand.repository.GroupRepository;
import com.nakvasin.musicBand.repository.SongRepository;
import com.nakvasin.musicBand.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourReportService {
    private final GroupRepository groupRepository;
    private final TourRepository tourRepository;
    private final SongRepository songRepository;

    public Mono<ServerResponse> getTourReport(ServerRequest serverRequest) {
        return tourRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tour -> songRepository.findAll()
                        .filter(song -> song.getGroupId().equals(tour.getGroupId()))
                        .collectList()
                        .publishOn(Schedulers.boundedElastic())
                        .map(songsList -> TourReport.builder()
                                .groupName(groupRepository.findById(tour.getGroupId())
                                        .flatMap(group -> Mono.justOrEmpty(group.getName()))
                                        .blockOptional()
                                        .orElse(""))
                                .programName(tour.getProgramName())
                                .startDate(tour.getStartDate())
                                .endDate(tour.getEndDate())
                                .place(tour.getLocation())
                                .songs(songsList)
                                .build()))
                .collectList()
                .flatMap(reportList -> ServerResponse.ok().bodyValue(reportList))
                .log();
    }
}
