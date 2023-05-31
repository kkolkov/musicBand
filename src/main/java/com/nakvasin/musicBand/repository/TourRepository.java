package com.nakvasin.musicBand.repository;

import com.nakvasin.musicBand.entity.Tour;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends ReactiveMongoRepository<Tour, String> {
}
