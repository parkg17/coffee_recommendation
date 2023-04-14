package com.gyeol.coffee.controller;

import com.gyeol.coffee.dto.request.*;
import com.gyeol.coffee.dto.response.*;
import com.gyeol.coffee.service.CoffeeReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class MainController {
    private final CoffeeReviewService coffeeReviewService;

    @GetMapping("/item")
    public  GetItemRecommendationResponse getItemRecommendation(@RequestBody GetItemRecommendationRequest req) {

        return coffeeReviewService.getItemRecommendation(req.getCondition1(), req.getCondition2(), req.getCondition3());
    }

    @GetMapping("/similar")
    public GetSimilarityRecommendationResponse getSimilarRecommendation(@RequestBody GetSimilarityRecommendationRequest req)
            throws IOException {
        return coffeeReviewService.getSimilarityRecommendation(req);
    }

    @GetMapping("/market")
    public GetMarketRecommendationResponse getSimilarRecommendation(@RequestBody GetMarketRecommendationRequest req) {
        return coffeeReviewService.getMarketRecommendation(req.getMarket());
    }
}
