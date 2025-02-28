package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.domain.HomeCategorySection;
import com.urban.oasis.urban.oasis_ecommerce.model.Deal;
import com.urban.oasis.urban.oasis_ecommerce.model.Home;
import com.urban.oasis.urban.oasis_ecommerce.model.HomeCategory;
import com.urban.oasis.urban.oasis_ecommerce.repository.DealRepository;
import com.urban.oasis.urban.oasis_ecommerce.repository.HomeRepository;
import com.urban.oasis.urban.oasis_ecommerce.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeRepository homeRepository;
    private final DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                .toList();

        List<Deal> createdDeals = new ArrayList<>();

        if (dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                    .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                    .map(category -> new Deal(null, 30, category))
                    .collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        }
        else createdDeals = dealRepository.findAll();

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);
        return home;
    }
}
