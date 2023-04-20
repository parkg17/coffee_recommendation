package com.gyeol.coffee.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gyeol.coffee.model.dao.CoffeeReview;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetMarketRecommendationResponse {
    Integer rc;

    List<CoffeeReview> result;
}
