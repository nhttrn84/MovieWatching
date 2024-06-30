package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviesByCatDTO {
    private String title;
    private String subTitle;
    private String poster;
    private String status;
    private String link;
}
