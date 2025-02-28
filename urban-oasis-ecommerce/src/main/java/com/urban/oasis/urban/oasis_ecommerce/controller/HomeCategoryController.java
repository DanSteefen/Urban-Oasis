package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.model.Home;
import com.urban.oasis.urban.oasis_ecommerce.model.HomeCategory;
import com.urban.oasis.urban.oasis_ecommerce.service.HomeCategoryService;
import com.urban.oasis.urban.oasis_ecommerce.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/homeCategories")
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategory) {

        List<HomeCategory> categories = homeCategoryService.createHomeCategories(homeCategory);
        Home home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategories() {

        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id,
                                                           @RequestBody HomeCategory homeCategory) throws Exception{

        HomeCategory updateCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return ResponseEntity.ok(updateCategory);
    }
}
