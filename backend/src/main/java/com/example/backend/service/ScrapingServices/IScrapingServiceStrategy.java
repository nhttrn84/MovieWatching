package com.example.backend.service.ScrapingServices;

//import com.example.backend.dto.NovelDownloadContentDTO;
import com.example.backend.response.*;

public interface IScrapingServiceStrategy {
    public MoviesByCatResponse getMoviesByCategory(String category, int page) throws Exception;

    public MoviesByCountryResponse getMoviesByCountry(String category, int page) throws Exception;

    public MovieDetailsResponse getMovieDetail(String type, String title) throws Exception;

    public MovieEpisodeResponse getMovieEpisode(String type, String title) throws Exception;

    public CategoriesResponse getCategories() throws Exception;

    public CountriesResponse getCountries() throws Exception;

    public SearchResponse getSearchResult(String keyword, int page) throws Exception;
}