package com.gyeol.coffee.model.service;


import com.gyeol.coffee.model.dao.CoffeeReview;
import com.gyeol.coffee.model.repository.CoffeeReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CoffeeReviewDomainService {
    private final CoffeeReviewRepository bedsoreRepository;

    public Optional<CoffeeReview> getCoffeeItem(Integer index){
        return bedsoreRepository.findByIndex(index);
    }
}
