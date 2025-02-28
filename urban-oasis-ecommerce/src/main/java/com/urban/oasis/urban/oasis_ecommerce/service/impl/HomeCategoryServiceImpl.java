package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.model.HomeCategory;
import com.urban.oasis.urban.oasis_ecommerce.repository.HomeCategoryRepository;
import com.urban.oasis.urban.oasis_ecommerce.service.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {

        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories) {

        if (homeCategoryRepository.findAll().isEmpty()) {
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {

        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found.."));
        if (category.getImg() != null) {
            existingHomeCategory.setImg(category.getImg());
        }
        if (category.getCategoryId() != null){
            existingHomeCategory.setCategoryId(category.getCategoryId());
        }
        return homeCategoryRepository.save(existingHomeCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }
}
