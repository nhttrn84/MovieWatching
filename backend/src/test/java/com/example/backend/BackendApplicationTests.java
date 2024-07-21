package com.example.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.response.CategoriesResponse;
import com.example.backend.response.CountriesResponse;
import com.example.backend.response.MovieDetailsResponse;
import com.example.backend.response.MovieEpisodeResponse;
import com.example.backend.response.MoviesByCatResponse;
import com.example.backend.response.SearchResponse;
import com.example.backend.service.ScrapingServices.PhimmoiScrapingService;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	private PhimmoiScrapingService phimmoiScrapingService;

	@Test
	void testGetCategories() throws Exception {
		CategoriesResponse response = phimmoiScrapingService.getCategories();
		assertNotNull(response);
		System.out.println(response.getCategories());
	}

	@Test
	void testGetMovies() throws Exception {
		MoviesByCatResponse response = phimmoiScrapingService.getMoviesByCategory("phim-co-trang", 1);
		assertNotNull(response);
		System.out.println(response.getMovies());
	}

	@Test
	void testGetMovieDetails() throws Exception {
		MovieDetailsResponse response = phimmoiScrapingService.getMovieDetail("samurai-mat-xanh", "phim-bo");
		assertNotNull(response);
		System.out.println(response.getMovieDetails());
	}

	@Test
	void testGetMovieEpisode() throws Exception {
		MovieEpisodeResponse response = phimmoiScrapingService
				.getMovieEpisode("phim-le/nang-cap", "phim-le");
		assertNotNull(response);
		System.out.println(response.getMovieEpisodes());
	}

	@Test
	void testGetMovieEpisodePhimBo() throws Exception {
		MovieEpisodeResponse response = phimmoiScrapingService
				.getMovieEpisode("xem-phim/the-gioi-ma-quai-2-sweet-home-2-tap-1", "phim-bo");
		assertNotNull(response);
		System.out.println(response.getMovieEpisodes());
	}

	@Test
	void testSearchMovie() throws Exception {
		SearchResponse response = phimmoiScrapingService
				.getSearchResult("nâng", 1);
		assertNotNull(response);
		System.out.println(response.getMovies());
	}
}
