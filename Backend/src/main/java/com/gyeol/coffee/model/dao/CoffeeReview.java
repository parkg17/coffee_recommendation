package com.gyeol.coffee.model.dao;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coffee_bin_review")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EntityListeners(AuditingEntityListener.class)
public class CoffeeReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
