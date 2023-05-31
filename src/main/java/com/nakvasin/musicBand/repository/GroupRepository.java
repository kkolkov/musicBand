package com.nakvasin.musicBand.repository;

import com.nakvasin.musicBand.entity.Group;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GroupRepository extends ReactiveMongoRepository<Group, String> {
    Mono<Group> getGroupByName(String groupName);
}

