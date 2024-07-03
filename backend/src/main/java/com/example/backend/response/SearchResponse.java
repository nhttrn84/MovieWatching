package com.example.backend.response;

import com.example.backend.dto.MoviesByCatDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    @JsonProperty("movies")
    private List<MoviesByCatDTO> movies;

    @JsonProperty("currentPage")
    private int currentPage;
}