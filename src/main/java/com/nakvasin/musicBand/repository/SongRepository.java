package com.nakvasin.musicBand.repository;

import com.nakvasin.musicBand.entity.Song;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SongRepository extends ReactiveMongoRepository<Song, String> {
    Mono<Song> findByName(String songName);
}
