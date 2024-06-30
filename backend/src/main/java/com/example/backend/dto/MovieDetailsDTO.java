package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDTO {
    private String title;
    private String subTitle;
    private String poster;
    private String date;
    private String status;
    private String rating;
    private List<String> categories;
    private List<EpisodeDTO> episodes;
    private String info;
    private List<PersonDTO> creators;
    private List<PersonDTO> actors;
}
