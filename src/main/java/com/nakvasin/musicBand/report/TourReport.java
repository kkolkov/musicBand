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
    private String groupName;
    private String programName;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Song> songs;
}
