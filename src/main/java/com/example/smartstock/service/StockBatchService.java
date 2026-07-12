package com.example.smartstock.service;

import com.example.smartstock.model.Product;
import com.example.smartstock.model.StockBatch;
import com.example.smartstock.repository.StockBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockBatchService {

    @Autowired
    private StockBatchRepository stockBatchRepository;

    @Autowired
    private ProductService productService;

    // Add new stock (e.g., a fresh delivery arrives)
    public StockBatch addStock(Long productId, Double quantity, LocalDate expiryDate) {
        Product product = productService.getProductById(productId);

        StockBatch batch = new StockBatch();
        batch.setProduct(product);
        batch.setQuantity(quantity);
        batch.setExpiryDate(expiryDate);
        batch.setAddedDate(LocalDate.now());
        batch.setStatus(StockBatch.BatchStatus.ACTIVE);

        return stockBatchRepository.save(batch);
    }

    // FIFO deduction: use up the soonest-expiring stock first
    public String deductStock(Long productId, Double quantityToDeduct) {
        List<StockBatch> batches = stockBatchRepository
                .findByProductIdAndStatusOrderByExpiryDateAsc(productId, StockBatch.BatchStatus.ACTIVE);

        double remaining = quantityToDeduct;

        for (StockBatch batch : batches) {
            if (remaining <= 0) break;

            if (batch.getQuantity() <= remaining) {
                // This whole batch gets used up
                remaining -= batch.getQuantity();
                batch.setQuantity(0.0);
                batch.setStatus(StockBatch.BatchStatus.DEPLETED);
            } else {
                // This batch has more than enough — partial deduction
                batch.setQuantity(batch.getQuantity() - remaining);
                remaining = 0.0;
            }
            stockBatchRepository.save(batch);
        }

        if (remaining > 0) {
            return "Warning: Not enough stock. Short by " + remaining + " units.";
        }
        return "Stock deducted successfully using FIFO.";
    }

    public List<StockBatch> getBatchesForProduct(Long productId) {
        return stockBatchRepository.findByProductIdAndStatusOrderByExpiryDateAsc(
                productId, StockBatch.BatchStatus.ACTIVE);
    }
}