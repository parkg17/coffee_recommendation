package com.gyeol.coffee.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetSimilarityRecommendationRequest {
    Integer rc;

    String growing_region;
    String tree_variety;
    String roast_level;
}
