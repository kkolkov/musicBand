package com.nakvasin.musicBand.config;

import com.nakvasin.musicBand.entity.Group;
import com.nakvasin.musicBand.entity.Member;
import com.nakvasin.musicBand.entity.Song;
import com.nakvasin.musicBand.entity.Tour;
import com.nakvasin.musicBand.repository.GroupRepository;
import com.nakvasin.musicBand.repository.SongRepository;
import com.nakvasin.musicBand.repository.TourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class GroupConfig {



    @Bean
    public CommandLineRunner init(GroupRepository groupRepository, SongRepository songRepository, TourRepository tourRepository) {
        return args -> {
            Member jamesAlanHetfield = Member.builder()
                    .name("James Hetfield")
                    .age(59)
                    .role("Vocal")
                    .build();

            Group metallica = Group.builder()
                    .id("646d88c1ab58915f008cb565")
                    .name("Metallica")
                    .year(1981)
                    .country("USA")
                    .members(List.of(jamesAlanHetfield))
                    .chartPosition(6)
                    .build();

            Member angusYoung = Member.builder()
                    .name("Angus McKinnon Young")
                    .age(68)
                    .role("Guitar")
                    .build();

            Member malcolmYoung = Member.builder()
                    .name("Malcolm Mitchell Young")
                    .age(64)
                    .role("Guitar")
                    .build();

            Group acdc = Group.builder()
                    .id("646d88c1ab58915f008cb566")
                    .name("AC/DC")
                    .year(1973)
                    .country("Australia")
                    .members(List.of(angusYoung, malcolmYoung))
                    .chartPosition(1)
                    .build();

            Song enterSandman = Song.builder()
                    .name("Enter Sandman")
                    .composer("James Hetfield")
                    .lyricist("James Hetfield, Lars Ulrich")
                    .groupId("646d88c1ab58915f008cb565")
                    .dateCreated(LocalDate.of(1991, 7, 12))
                    .build();

            Song masterOfPuppets = Song.builder()
                    .name("Master of Puppets")
                    .composer("Cliff Burton")
                    .lyricist("James Hetfield, Lars Ulrich")
                    .groupId("646d88c1ab58915f008cb565")
                    .dateCreated(LocalDate.of(1986, 3, 3))
                    .build();

            Song backInBlack = Song.builder()
                    .name("Back in Black")
                    .composer("Angus Young, Malcolm Young")
                    .lyricist("Brian Johnson")
                    .groupId("646d88c1ab58915f008cb566")
                    .dateCreated(LocalDate.of(1980, 7, 25))
                    .build();

            Song highwayToHell = Song.builder()
                    .name("Highway to Hell")
                    .composer("Angus Young, Malcolm Young, Bon Scott")
                    .lyricist("Angus Young, Malcolm Young, Bon Scott")
                    .groupId("646d88c1ab58915f008cb566")
                    .dateCreated(LocalDate.of(1979, 7, 27))
                    .build();

            Tour metallicaTour = Tour.builder()
                    .id("646d")
                    .groupId("646d88c1ab58915f008cb565")
                    .programName("WorldWired Tour")
                    .startDate(LocalDate.of(2022, 6, 15))
                    .endDate(LocalDate.of(2022, 8, 22))
                    .location("USA")
                    .ticketPrice(150)
                    .build();

            Flux.just(metallica, acdc)
                    .flatMap(groupRepository::save)
                    .thenMany(groupRepository.findAll())
                    .subscribe(System.out::println);

            Flux.just(enterSandman, masterOfPuppets, backInBlack, highwayToHell)
                    .flatMap(songRepository::save)
                    .thenMany(songRepository.findAll())
                    .subscribe(System.out::println);

            Flux.just(metallicaTour)
                    .flatMap(tourRepository::save)
                    .thenMany(tourRepository.findAll())
                    .subscribe(System.out::println);
        };
    }
}
