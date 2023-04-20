package com.gyeol.coffee.model.repository;


import com.gyeol.coffee.model.dao.CoffeeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeReviewRepository extends JpaRepository<CoffeeReview, Long> {
    Optional<CoffeeReview> findByIndex(Integer index);
}
