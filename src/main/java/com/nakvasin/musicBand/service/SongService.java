package com.nakvasin.musicBand.service;

import com.nakvasin.musicBand.dto.SongDTO;
import com.nakvasin.musicBand.repository.GroupRepository;
import com.nakvasin.musicBand.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {
    private final GroupRepository groupRepository;
    private final SongRepository songRepository;

    public Mono<ServerResponse> getTopInChartRepertoire(ServerRequest serverRequest) {
        Flux<SongDTO> topInChartSongs = groupRepository.findAll()
                .filter(group -> group.getChartPosition() == 1)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(group -> songRepository.findAll()
                        .filter(song -> song.getGroupId().equals(group.getId()))
                        .collectList()
                        .map(songs -> SongDTO.builder()
                                .name(group.getName())
                                .songs(songs)
                                .build()));
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(topInChartSongs, SongDTO.class);
    }


    public Mono<ServerResponse> getLyricistComposerAndDateCreated(ServerRequest serverRequest) {
        String songName = serverRequest.pathVariable("name");
        return songRepository.findByName(songName)
                .flatMap(song -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(SongDTO.builder()
                                .lyricist(song.getLyricist())
                                .composer(song.getComposer())
                                .dateCreated(song.getDateCreated())
                                .build()));
    }
}
