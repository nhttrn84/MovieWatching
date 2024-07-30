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
import com.example.backend.dto.MovieEpisodesDTO;
import com.example.backend.dto.MoviesByCatDTO;
import com.example.backend.dto.PersonDTO;
import com.example.backend.utils.StringManipulator;
import com.example.backend.response.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Duration;

@Service
public class PhimmoiScrapingService implements IScrapingServiceStrategy {
    @Autowired
    private StringManipulator stringManipulator;

    public void setStringManipulator(StringManipulator stringManipulator) {
        this.stringManipulator = stringManipulator;
    }

    private static final Logger log = LoggerFactory.getLogger(PhimmoiScrapingService.class);

    private static List<MoviesByCatDTO> extractMoviesFromPage(Document document) {
        Elements movieElements = document.select("div.items article");
        List<MoviesByCatDTO> movieList = new ArrayList<>();

        for (Element movieElement : movieElements) {
            MoviesByCatDTO movie = MoviesByCatDTO.builder()
                    .title(movieElement.select("div.data h3 a").text())
                    .subTitle(movieElement.select("div.data span span").text())
                    .poster(movieElement.select("div.poster img").attr("src"))
                    .status(movieElement.select("div.trangthai").text())
                    .link(movieElement.select("div.data h3 a").attr("href"))
                    .build();

            movieList.add(movie);
        }
        return movieList;
    }

    private static List<MoviesByCatDTO> extractMoviesFromSearchResult(Document document) {
        Elements movieElements = document.getElementsByClass("result-item");
        List<MoviesByCatDTO> movieList = new ArrayList<>();

        for (Element movieElement : movieElements) {
            MoviesByCatDTO movie = MoviesByCatDTO.builder()
                    .title(movieElement.select("div.title a").text())
                    .subTitle("")
                    .poster(movieElement.select("img").attr("src"))
                    .status(movieElement.select("span.movies").text())
                    .link(movieElement.select("div.thumbnail a").attr("href"))
                    .build();

            movieList.add(movie);
        }
        return movieList;
    }

    private static MovieDetailsDTO extractMovieDetail(Document document) {
        String title = document.select("div.data h1").text();
        String subTitle = document.select("div.data div.extra span.valor").text();
        String poster = document.select("div.poster img").attr("src");
        String date = document.select("div.data div.extra span.date").text();
        String status = document.select("div.data div.movie_label span.item-label").text();
        String rating = document.select("div.starstruck-rating span.dt_rating_vgs").text();
        String info = document.select("div#info p").text();
        Elements categoryElements = document.select("div.sgeneros a");
        List<String> categories = new ArrayList<>();
        for (Element categoryElement : categoryElements) {
            categories.add(categoryElement.text());
        }

        Elements episodeElements = document.select("ul.episodios li");
        List<EpisodeDTO> episodes = new ArrayList<>();
        for (Element episodeElement : episodeElements) {
            episodes.add(EpisodeDTO.builder()
                    .title(episodeElement.text())
                    .link(episodeElement.select("div.episodiotitle a").attr("href"))
                    .build());
        }

        Elements creatorElements = document.select("div#cast h2:contains(Creator) + div.persons .person");
        List<PersonDTO> creators = new ArrayList<>();

        for (Element creatorElement : creatorElements) {
            creators.add(PersonDTO.builder()
                    .name(creatorElement.select("div.name a").text())
                    .image(creatorElement.select("div.img a img").attr("src"))
                    .character(creatorElement.select("div.caracter").text())
                    .build());
        }

        Elements actorElements = document.select("div#cast h2:contains(Diễn viên) + div.persons .person");
        List<PersonDTO> actors = new ArrayList<>();

        for (Element actorElement : actorElements) {
            actors.add(PersonDTO.builder()
                    .name(actorElement.select("div.name a").text())
                    .image(actorElement.select("div.img a img").attr("src"))
                    .character(actorElement.select("div.caracter").text())
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
                .episodes(episodes)
                .info(info)
                .creators(creators)
                .actors(actors)
                .build();
    }

    @Override
    public CategoriesResponse getCategories() throws Exception {
        String url = "https://phimmoiiii.net/";
        try {
            // Send an HTTP GET request to the website
            Document document = Jsoup.connect(url).get();
            List<String> categoryList = new ArrayList<>();
            Element categoryMenu = document.getElementById("menu-item-13");

            if (categoryMenu != null) {
                Elements categoryListElements = categoryMenu.select("ul.sub-menu li a");

                for (Element categoryElement : categoryListElements) {
                    categoryList.add(categoryElement.text());
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
        String url = "https://phimmoiiii.net/";
        try {
            // Send an HTTP GET request to the website
            Document document = Jsoup.connect(url).get();
            List<String> categoryList = new ArrayList<>();
            Element categoryMenu = document.getElementById("menu-item-11");

            if (categoryMenu != null) {
                Elements categoryListElements = categoryMenu.select("ul.sub-menu li a");

                for (Element categoryElement : categoryListElements) {
                    categoryList.add(categoryElement.text());
                }
            }

            return CountriesResponse.builder()
                    .countries(categoryList)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching categories", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public MoviesByCatResponse getMoviesByCategory(String category, int page) throws Exception {
        String normalizedCategory = stringManipulator.modify(category);
        String url = "https://phimmoiiii.net/the-loai/" + normalizedCategory + "/page/";

        try {
            Document document = Jsoup.connect(url + page).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromPage(document);

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
        String url = "https://phimmoiiii.net/quoc-gia/" + normalizedCategory + "/page/";

        try {
            Document document = Jsoup.connect(url + page).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromPage(document);

            return MoviesByCountryResponse.builder()
                    .movies(moviesByCat)
                    .currentPage(page)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by country");
        }
    }

    @Override
    public MovieDetailsResponse getMovieDetail(String title) throws Exception {
        String normalizedTitle = stringManipulator.modify(title);
        String url = "https://phimmoiiii.net/" + normalizedTitle;

        try {
            Document document = Jsoup.connect(url).get();
            MovieDetailsDTO movieDetails = extractMovieDetail(document);
            log.info("movie details: {}", movieDetails);
            return MovieDetailsResponse.builder()
                    .movieDetails(movieDetails)
                    .build();
        } catch (IOException e) {
            throw new Exception("Error fetching movies by category");
        }
    }

    @Override
    public MovieEpisodeResponse getMovieEpisode(String title) throws Exception {
        String normalizedTitle = stringManipulator.modify(title);
        String url = "https://phimmoiiii.net/" + normalizedTitle;

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        // Initialize WebDriver with ChromeOptions
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // Open the page
            driver.get(url);
            WebElement iframe = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='pframe']/iframe[1]")));
            String src = iframe.getAttribute("src");
            String extractedLink = src.substring(src.indexOf("=") + 1);

            MovieEpisodesDTO movieLink = MovieEpisodesDTO.builder()
                    .video(extractedLink)
                    .build();

            return MovieEpisodeResponse.builder()
                    .movieEpisodes(movieLink)
                    .build();

        } catch (Exception e) {
            throw new Exception("Error fetching movies by episode");
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    @Override
    public SearchResponse getSearchResult(String keyword, int page) throws Exception {
        String normalizedKeyword = stringManipulator.modify(keyword);
        String url = "https://phimmoiiii.net/page/" + page + "?s=" + normalizedKeyword;

        try {
            Document document = Jsoup.connect(url).get();
            List<MoviesByCatDTO> moviesByCat = extractMoviesFromSearchResult(document);
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