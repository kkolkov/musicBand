package com.nakvasin.musicBand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "songs")
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    private String id;
    private String name;
    private String composer;
    private String lyricist;
    private String groupId;
    private LocalDate dateCreated;

}