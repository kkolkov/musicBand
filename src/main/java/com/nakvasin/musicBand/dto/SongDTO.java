package com.nakvasin.musicBand.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakvasin.musicBand.entity.Song;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SongDTO {
    private String name;
    private String lyricist;
    private String composer;
    private List<Song> songs;
    private LocalDate dateCreated;
}
