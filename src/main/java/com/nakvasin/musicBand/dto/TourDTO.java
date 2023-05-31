package com.nakvasin.musicBand.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TourDTO {
    private String groupName;
    private String location;
    private Integer duration;
    private Integer ticketPrice;
}
