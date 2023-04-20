package com.gyeol.coffee.dto.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class CoffeeReviewData {
    Integer index;
    String growingRegion;
    String treeVariety;
    String roastLevel;
    String agtron;
    Integer aroma;
    Integer acidity;
    Integer body;
    Integer flavor;
    Integer aftertaste;
}
