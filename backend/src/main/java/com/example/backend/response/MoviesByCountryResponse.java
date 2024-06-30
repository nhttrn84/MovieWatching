package com.example.backend.response;

import com.example.backend.dto.MoviesByCatDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MoviesByCountryResponse {
    @JsonProperty("movies")
    private List<MoviesByCatDTO> movies;

    @JsonProperty("currentPage")
    private int currentPage;
}