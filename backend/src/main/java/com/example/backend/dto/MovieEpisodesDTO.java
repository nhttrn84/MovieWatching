package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.backend.dto.EpisodeDTO;
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
public class MovieEpisodesDTO {
    private String video;
}
