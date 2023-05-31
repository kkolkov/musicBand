package com.nakvasin.musicBand.report;

import com.nakvasin.musicBand.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourReport {
    String groupName;
    String programName;
    String place;
    LocalDate startDate;
    LocalDate endDate;
    List<Song> songs;
}
