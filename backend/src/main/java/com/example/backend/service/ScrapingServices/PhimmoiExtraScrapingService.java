package com.example.backend.service.ScrapingServices;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.EpisodeDTO;
import com.example.backend.dto.MovieDetailsDTO;
import com.example.backend.dto.MoviesByCatDTO;
import com.example.backend.dto.PersonDTO;
import com.example.backend.factory.ScrapingServiceFactory;
import com.example.backend.utils.StringManipulator;
import com.example.backend.response.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PhimmoiExtraScrapingService implements IScrapingServiceStrategy {
    @Autowired
    private StringManipulator stringManipulator;

    public void setStringManipulator(StringManipulator stringManipulator) {
        this.stringManipulator = stringManipulator;
    }

    private static final Logger log = LoggerFactory.getLogger(PhimmoiExtraScrapingService.class);

    private static List<MoviesByCatDTO> extractMoviesFromPage(Document document) {
        Elements movieElements = document.select("ul.list-film li");
        List<MoviesByCatDTO> movieList = new ArrayList<>();

        for (Element movieElement : movieElements) {
            MoviesByCatDTO movie = MoviesByCatDTO.builder()
                    .title(movieElement.select("a h3").text())
                    .subTitle("")
                    .poster(movieElement.select("a img").attr("src"))
                    .status(movieElement.select("span div").text())
                    .link(movieElement.select("a").attr("href"))
                    .build();

            movieList.add(movie);
        }
        return movieList;
    }

    private static MovieDetailsDTO extractMovieDetail(Document document) {
        String title = document.select("div.image div.text h1").text();
        String subTitle = document.select("div.image div.text h2").text();
        String poster = document.select("div.image img").attr("src");
        String date = document.select("div.film-info div.text ul.block-film li:nth-of-type(2) a").text();
        String status = document.select("div.film-info div.text ul.block-film li:nth-of-type(1) span").text();
        String rating = document.select("div.social div.box-rating div#div_average span.average").text();
        String info = document.select("div#film-content").text();
        Elements categoryElements = document.select("div.film-info div.text ul li:nth-of-type(4) a");
        List<String> categories = new ArrayList<>();
        for (Element categoryElement : categoryElements) {
            categories.add(categoryElement.text());
        }

        Elements creatorElements = document
                .select("div.film-info div.text ul li:nth-of-type(5) span[itemprop='director']");
        List<PersonDTO> creators = new ArrayList<>();

        for (Element creatorElement : creatorElements) {
            creators.add(PersonDTO.builder()
                    .name(creatorElement.select("span[itemprop='name']").text())
                    .image("")
                    .character("Director")
                    .build());
        }

        Elements actorElements = document.select("div.film-info div.text ul li:nth-of-type(8) a");
        List<PersonDTO> actors = new ArrayList<>();

        log.info("actorEle {}", actorElements);
        for (Element actorElement : actorElements) {
            actors.add(PersonDTO.builder()
                    .name(actorElement.select("a").text())
                    .image("")
                    .character("Actor")
                    .build());
        }

        return MovieDetailsDTO.builder()
                .title(title)
                .subTitle(subTitle)
                .poster(poster)
                .date(date)
                .status(status)
                .rating(rating)
                .categories(categories)
                .episodes(null)
                .info(info)
                .creators(creators)
                .actors(actors)
                .build();
    }

    @Override
    public CategoriesResponse getCategories() throws Exception {
        String url = "https://phimmoichillu.net//";
        try {
            // Send an HTTP GET request to the website
            Document document = Jsoup.connect(url).get();
            List<String> categoryList = new ArrayList<>();
            Element categorySelect = document.select("a[title='Thể Loại Phim']").first();

            if (categorySelect != null) {
                Element categoryMenu = categorySelect.parent().select("ul.sub-menu.span-6").first();
                if (categoryMenu != null) {
                    Elements categoryElements = categoryMenu.select("a");
                    for (Element categoryElement : categoryElements) {
                        categoryList.add(categoryElement.text());
                    }
                }
            }

            log.info("Category list: {}", categoryList);
            return CategoriesResponse.builder()
                    .categories(categoryList)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching categories", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public CountriesResponse getCountries() throws Exception {
        String url = "https://phimmoichillu.net//";
        try {
            // Send an HTTP GET request to the website
            Document document = Jsoup.connect(url).get();
            List<String> countryList = new ArrayList<>();
            Element countrySelect = document.select("a[title='Quốc Gia']").first();

            if (countrySelect != null) {
                Element countryMenu = countrySelect.parent().select("ul.sub-menu").first();
                if (countryMenu != null) {
                    Elements countryElements = countryMenu.select("a");
                    for (Element countryElement : countryElements) {
                        countryList.add(countryElement.text());
                    }
                }
            }

            return CountriesResponse.builder()
                    .countries(countryList)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching countries", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public MoviesByCatResponse getMoviesByCategory(String category, int page) throws Exception {
        String normalizedCategory = stringManipulator.modify(category);
        String url = "https://phimmoichillu.net/genre/" + normalizedCategory + "/page-";

        try {
            Document document = Jsoup.connect(url + page).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromPage(document);
            log.info("movies by category: {}", moviesByCat);
            return MoviesByCatResponse.builder()
                    .movies(moviesByCat)
                    .currentPage(page)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by category");
        }
    }

    @Override
    public MoviesByCountryResponse getMoviesByCountry(String category, int page) throws Exception {
        String normalizedCategory = stringManipulator.modify(category);
        String url = "https://phimmoichillu.net/country/" + normalizedCategory + "/page-";

        try {
            Document document = Jsoup.connect(url + page).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromPage(document);
            log.info("movies by category: {}", moviesByCat);
            return MoviesByCountryResponse.builder()
                    .movies(moviesByCat)
                    .currentPage(page)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by category");
        }
    }

    @Override
    public MovieDetailsResponse getMovieDetail(String title, String type) throws Exception {
        String normalizedTitle = stringManipulator.modify(title);
        String url = "https://phimmoichillu.net/info/" + normalizedTitle;

        try {
            Document document = Jsoup.connect(url).get();
            MovieDetailsDTO movieDetails = extractMovieDetail(document);

            return MovieDetailsResponse.builder()
                    .movieDetails(movieDetails)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by category");
        }
    }

    @Override
    public MovieEpisodeResponse getMovieEpisode(String title, String type) throws Exception {
        String normalizedTitle = stringManipulator.modify(title);
        String url = "https://phimmoichillu.net/xem/" + normalizedTitle;

        try {
            Document document = Jsoup.connect(url).get();
            log.info("document: {}", document);

            return MovieEpisodeResponse.builder()
                    .movieEpisodes(null)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by category");
        }
    }

    @Override
    public SearchResponse getSearchResult(String keyword, int page) throws Exception {
        String normalizedKeyword = stringManipulator.modify(keyword);
        String url = "https://phimmoiiii.netpage/" + page + "?s=" + normalizedKeyword;

        try {
            Document document = Jsoup.connect(url).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromPage(document);
            log.info("movies by search: {}", moviesByCat);
            return SearchResponse.builder()
                    .movies(moviesByCat)
                    .currentPage(page)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by search");
        }
    }
}
