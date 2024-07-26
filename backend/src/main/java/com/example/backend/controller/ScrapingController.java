package com.example.backend.controller;

import com.example.backend.response.*;
import com.example.backend.service.ScrapingServices.IScrapingServiceStrategy;
import com.example.backend.service.ScrapingServices.PhimmoiScrapingService;
import com.example.backend.factory.ScrapingServiceFactory;
import com.example.backend.utils.MessageKeys;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class ScrapingController {

    @Autowired
    private PhimmoiScrapingService phimmoiScrapingService;
    private static final Logger log = LoggerFactory.getLogger(ScrapingController.class);

    // Get all categories
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            CategoriesResponse categoriesRes = phimmoiScrapingService.getCategories();
            return ResponseEntity.ok(BaseResponse.<CategoriesResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_CATEGORIES_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(categoriesRes)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<CategoriesResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_CATEGORIES_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get all countries
    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        try {
            CountriesResponse countriesRes = phimmoiScrapingService.getCountries();
            return ResponseEntity.ok(BaseResponse.<CountriesResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_COUNTRIES_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(countriesRes)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<CountriesResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_COUNTRIES_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get movies by category
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getMoviesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "1") int page) {
        try {
            MoviesByCatResponse movies = phimmoiScrapingService.getMoviesByCategory(category, page);
            return ResponseEntity.ok(BaseResponse.<MoviesByCatResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_MOVIESBYCAT_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(movies)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<MoviesByCatResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_MOVIESBYCAT_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get movies by country
    @GetMapping("/country/{country}")
    public ResponseEntity<?> getMoviesByCountry(
            @PathVariable String country,
            @RequestParam(defaultValue = "1") int page) {
        try {
            MoviesByCountryResponse movies = phimmoiScrapingService.getMoviesByCountry(country, page);
            return ResponseEntity.ok(BaseResponse.<MoviesByCountryResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_MOVIESBYCOUNTRY_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(movies)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<MoviesByCountryResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_MOVIESBYCOUNTRY_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get movie details
    @GetMapping("/detail/{title}")
    public ResponseEntity<?> getMovieDetails(
            @PathVariable String title) {
        try {
            MovieDetailsResponse movieDetails = phimmoiScrapingService.getMovieDetail(title);
            return ResponseEntity.ok(BaseResponse.<MovieDetailsResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_MOVIEDETAILS_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(movieDetails)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<MovieDetailsResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_MOVIEDETAILS_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get movie episode
    @GetMapping("/episode/{title}")
    public ResponseEntity<?> getMovieEpisode(
            @PathVariable String title) {
        try {
            MovieEpisodeResponse movieEpisode = phimmoiScrapingService.getMovieEpisode(title);
            return ResponseEntity.ok(BaseResponse.<MovieEpisodeResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_MOVIEEPISODE_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(movieEpisode)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<MovieEpisodeResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_MOVIEEPISODE_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    // Get search result
    @GetMapping("/search")
    public ResponseEntity<?> getSearchResult(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "1") int page) {
        try {
            SearchResponse searchResult = phimmoiScrapingService.getSearchResult(keyword, page);
            return ResponseEntity.ok(BaseResponse.<SearchResponse>builder()
                    .status("Successful")
                    .message(MessageKeys.GET_SEARCHRESULT_SUCCESSFULLY)
                    .status_code(HttpStatus.OK.value())
                    .data(searchResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.<SearchResponse>builder()
                    .status("Failed")
                    .message(MessageKeys.GET_SEARCHRESULT_FAILED)
                    .status_code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }
}
