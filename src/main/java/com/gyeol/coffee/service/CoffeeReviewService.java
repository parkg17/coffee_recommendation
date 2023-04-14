package com.gyeol.coffee.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyeol.coffee.dto.data.CoffeeReviewData;
import com.gyeol.coffee.dto.response.GetItemRecommendationResponse;
import com.gyeol.coffee.model.dao.CoffeeReview;
import com.gyeol.coffee.model.repository.CoffeeReviewRepository;
import com.gyeol.coffee.model.service.CoffeeReviewDomainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class CoffeeReviewService {
    private final CoffeeReviewDomainService coffeeReviewDomainService;
    private final CoffeeReviewRepository coffeeReviewRepository;
    private ObjectMapper objectMapper;

    public GetItemRecommendationResponse getItemRecommendation(String condition1, String condition2, String condition3)
            throws JsonProcessingException {
        log.info("[getItemRecommendation]");

        var coffeeReviewDatas = coffeeReviewRepository.findAll();
        String cond;
        Comparator<CoffeeReview> compare = Comparator.comparing(CoffeeReview::getAroma);
        for(int idx = 0; idx < 3; ++idx) {
            if(idx == 0) {
                cond = condition1;
            }
            else if(idx == 1) {
                cond = condition2;
            }
            else {
                cond = condition3;
            }
            // string compare
            if(cond.equals("aroma")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAroma).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAroma, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("acidity")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAcidity).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAcidity, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("body")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getBody).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getBody, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("flavor")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getFlavor).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getFlavor, Comparator.reverseOrder());
                }
            }
            else if(cond.equals("aftertaste")) {
                if(idx == 0) {
                    compare = Comparator.comparing(CoffeeReview::getAftertaste).reversed();
                }
                else {
                    compare = compare.thenComparing(CoffeeReview::getAftertaste, Comparator.reverseOrder());
                }
            }
        }

        coffeeReviewDatas = coffeeReviewDatas.stream().sorted(compare)
                .collect(Collectors.toList());

        return GetItemRecommendationResponse.builder()
                .rc(200)
                .result(coffeeReviewDatas)
                .build();
    }
    /*
    public GetSimilariltRecommendationResponse getSimilarityRecommendation() throws JsonProcessingException {
        log.info("[getItemRecommendation]");
    }*/
}
