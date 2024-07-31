package com.example.backend.service.ScrapingServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;
import java.util.zip.GZIPInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.EpisodeDTO;
import com.example.backend.dto.MovieDetailsDTO;
import com.example.backend.dto.MovieEpisodesDTO;
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
import java.util.zip.GZIPInputStream;

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
    public MovieDetailsResponse getMovieDetail(String type, String title) throws Exception {
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
    public MovieEpisodeResponse getMovieEpisode(String type, String title) throws Exception {
        String normalizedTitle = stringManipulator.modify(title);
        String url = "https://phimmoiiii.net/" + normalizedTitle;
        try {
            // Step 1: Use Jsoup to load the initial page
            Document document = Jsoup.connect(url).get();

            // Step 2: Extract necessary information from the initial page
            String videoPageUrl = extractVideoPageUrl(document, url);

            MovieEpisodesDTO movieLink = MovieEpisodesDTO.builder()
                    .video(videoPageUrl)
                    .build();
            return MovieEpisodeResponse.builder()
                    .movieEpisodes(movieLink)
                    .build();

        } catch (IOException e) {
            throw new Exception("Error getting movie episode");
        }
    }

    private static String extractVideoPageUrl(Document document, String link) throws Exception {
        // Extract the necessary data attributes from the page
        Element playerOption = document.selectFirst("#player-option-1");
        if (playerOption == null) {
            throw new Exception("Player option not found");
        }

        String dataPost = playerOption.attr("data-post");
        String dataNume = playerOption.attr("data-nume");

        // Step 3: Send an HTTP request to the endpoint that returns the video URL
        String ajaxUrl = "https://phimmoiiii.net/wp-admin/admin-ajax.php";
        URL url = new URL(ajaxUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setRequestProperty("Referer", link);
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br, zstd");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
        connection.setDoOutput(true);

        String postData = "action=doo_player_ajax&post=" + dataPost + "&nume=" + dataNume + "&type=movie";
        System.out.println(postData);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = postData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new GZIPInputStream(connection.getInputStream()), "utf-8"));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            // Step 4: Parse the response to get the actual video URL
            String jsonResponse = content.toString();
            String videoUrl = parseVideoUrlFromResponse(jsonResponse);
            return videoUrl;
        } else {
            throw new Exception("Failed to connect: HTTP error code : " + responseCode);
        }
    }

    private static String parseVideoUrlFromResponse(String jsonResponse) {
        // Implement JSON parsing logic to extract the video URL from the response
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getString("embed_url");
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
