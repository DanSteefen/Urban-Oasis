package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeCategoryService {

    HomeCategory createHomeCategory(HomeCategory homeCategory);
    List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories);
    HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception;
    List<HomeCategory> getAllHomeCategories();
}
