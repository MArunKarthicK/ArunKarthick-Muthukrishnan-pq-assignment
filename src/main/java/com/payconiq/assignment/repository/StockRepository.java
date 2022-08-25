package com.payconiq.assignment.repository;

import com.payconiq.assignment.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long> {

    Optional<StockEntity> findByStockKey(String stockKey);
}
