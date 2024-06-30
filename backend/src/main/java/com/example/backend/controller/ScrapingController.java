package com.example.backend.controller;

import com.example.backend.response.CategoriesResponse;
import com.example.backend.service.ScrapingServices.PhimmoiScrapingService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScrapingController {

    @Autowired
    private PhimmoiScrapingService phimmoiScrapingService;

    @GetMapping("/categories")
    public CategoriesResponse getCategories() {
        try {
            return phimmoiScrapingService.getCategories();
        } catch (Exception e) {
            // Handle exception (e.g., return an error response)
            return CategoriesResponse.builder().categories(new ArrayList<>()).build();
        }
    }
}
