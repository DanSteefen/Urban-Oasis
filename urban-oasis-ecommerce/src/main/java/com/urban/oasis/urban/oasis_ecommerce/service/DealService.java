package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.model.Deal;

import java.util.List;

public interface DealService {

    List<Deal> getDeals();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal, Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;
}
