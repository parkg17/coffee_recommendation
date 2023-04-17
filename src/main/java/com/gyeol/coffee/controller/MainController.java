package com.gyeol.coffee.controller;

import com.gyeol.coffee.dto.request.*;
import com.gyeol.coffee.dto.response.*;
import com.gyeol.coffee.service.CoffeeReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class MainController {
    private final CoffeeReviewService coffeeReviewService;

    // 항목별 추천
    @GetMapping("/item")
    public GetItemRecommendationResponse getItemRecommendation(@RequestParam("condition1") String condition1,
                                                               @RequestParam("condition2") String condition2,
                                                               @RequestParam("condition3") String condition3) {
        return coffeeReviewService.getItemRecommendation(condition1, condition2, condition3);
    }

    @PostMapping("/item")
    public GetItemRecommendationResponse getItemRecommendationPost(@RequestBody GetItemRecommendationRequest req) {

        return coffeeReviewService.getItemRecommendation(req.getCondition1(), req.getCondition2(), req.getCondition3());
    }

    // 유사도 추천
    @GetMapping("/similar")
    public GetSimilarityRecommendationResponse getSimilarRecommendation(@RequestParam("growing_region") String growing_region,
                                                                        @RequestParam("tree_variety") String tree_variety,
                                                                        @RequestParam("roast_level") String roast_level) throws IOException {
        return coffeeReviewService.getSimilarityRecommendation(growing_region, tree_variety, roast_level);
    }

    @PostMapping("/similar")
    public GetSimilarityRecommendationResponse getSimilarRecommendationPost(@RequestBody GetSimilarityRecommendationRequest req)
            throws IOException {
        return coffeeReviewService.getSimilarityRecommendation(req.getGrowing_region(), req.getTree_variety(), req.getRoast_level());
    }

    // 매장별 추천
    @GetMapping("/market")
    public GetMarketRecommendationResponse getSimilarRecommendation(@RequestParam("market") String market) {
        return coffeeReviewService.getMarketRecommendation(market);
    }

    @PostMapping("/market")
    public GetMarketRecommendationResponse getSimilarRecommendationPost(@RequestBody GetMarketRecommendationRequest req) {
        return coffeeReviewService.getMarketRecommendation(req.getMarket());
    }
}
