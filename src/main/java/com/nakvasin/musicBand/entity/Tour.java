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
@Document(collection = "tours")
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    @Id
    private String id;
    private String groupId;
    private String programName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private Integer ticketPrice;

}