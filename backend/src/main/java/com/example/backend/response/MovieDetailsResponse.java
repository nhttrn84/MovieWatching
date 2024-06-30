package com.example.backend.response;

import java.util.List;

import com.example.backend.dto.MovieDetailsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDetailsResponse {
    private MovieDetailsDTO movieDetails;
}