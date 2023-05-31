package com.nakvasin.musicBand.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GroupDTO {
    private String name;
    private int year;
    private String country;
}
