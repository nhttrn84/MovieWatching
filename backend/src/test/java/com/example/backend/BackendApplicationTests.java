package com.example.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.example.backend.dto.MovieEpisodesDTO;
import com.example.backend.response.CategoriesResponse;
import com.example.backend.response.CountriesResponse;
import com.example.backend.response.MovieDetailsResponse;
import com.example.backend.response.MovieEpisodeResponse;
import com.example.backend.response.MoviesByCatResponse;
import com.example.backend.response.SearchResponse;
import com.example.backend.service.ScrapingServices.PhimmoiScrapingService;
import com.example.backend.service.ScrapingServices.PhimmoiExtraScrapingService;

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
		MovieDetailsResponse response = phimmoiScrapingService.getMovieDetail("phim-bo/samurai-mat-xanh");
		assertNotNull(response);
		System.out.println(response.getMovieDetails());
	}

	@Test
	void testGetMovieEpisode() throws Exception {
		MovieEpisodeResponse response = phimmoiScrapingService
				.getMovieEpisode("phim-le/nang-cap");
		assertNotNull(response);
		System.out.println(response.getMovieEpisodes());
	}

	@Test
	void testGetMovieEpisodePhimBo() throws Exception {
		MovieEpisodeResponse response = phimmoiScrapingService
				.getMovieEpisode("xem-phim/nang-cap");
		assertNotNull(response);
		System.out.println(response.getMovieEpisodes());
	}

	@Test
	void testGetMovieEpisodeTest() throws Exception {
		String url = "https://phimmoiiii.net/phim-le/loan-the-dinh-tan-kiem";
		Document document = Jsoup.connect(url).get();

		System.out.println(document);
		document.select("//div[@class='pframe']/iframe[1]");
	}

	@Test
	void testSearchMovie() throws Exception {
		SearchResponse response = phimmoiScrapingService
				.getSearchResult("nâng", 1);
		assertNotNull(response);
		System.out.println(response.getMovies());
	}

	@Test
	void testGetMovieEpisode1() throws Exception {
		String url = "https://phimmoiiii.net/phim-le/loan-the-dinh-tan-kiem";

		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");

		// Initialize WebDriver with ChromeOptions
		WebDriver driver = new ChromeDriver(options);

		try {
			// Open the page
			driver.get(url);
			String pageSource = driver.getPageSource();
			System.out.println(pageSource);
			WebElement iframe = driver.findElement(By.xpath("//div[@class='pframe']/iframe[1]"));
			System.out.println(iframe);
			String src = iframe.getAttribute("src");
			String extractedLink = src.substring(src.indexOf("=") + 1);
			System.out.println("extractedLink");
			System.out.println(extractedLink);

		} catch (Exception e) {
			throw new Exception("Error fetching movies by episode");
		} finally {
			// Close the browser
			driver.quit();
		}
	}

	@Autowired
	private PhimmoiExtraScrapingService phimmoiExtraScrapingService;

	@Test
	void testGetMovie() throws Exception {
		MovieEpisodeResponse response = phimmoiExtraScrapingService
				.getMovieEpisode("phim-le/nang-cap");
		assertNotNull(response);
		System.out.println(response.getMovieEpisodes());
	}
}
