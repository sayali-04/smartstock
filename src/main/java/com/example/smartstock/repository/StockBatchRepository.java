package com.example.smartstock.repository;

import com.example.smartstock.model.StockBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long> {

    List<StockBatch> findByProductIdAndStatusOrderByExpiryDateAsc(Long productId, StockBatch.BatchStatus status);
}