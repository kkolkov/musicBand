package com.nakvasin.musicBand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    private String id;
    private String name;
    private int year;
    private String country;
    private List<Member> members;
    private int chartPosition;

}
