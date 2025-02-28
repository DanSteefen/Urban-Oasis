package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.model.Home;
import com.urban.oasis.urban.oasis_ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeService {

    public Home createHomePageData(List<HomeCategory> allCategories);
}
