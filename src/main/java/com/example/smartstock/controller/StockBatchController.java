package com.example.smartstock.controller;

import com.example.smartstock.model.StockBatch;
import com.example.smartstock.service.StockBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockBatchController {

    @Autowired
    private StockBatchService stockBatchService;

    @PostMapping("/add")
    public StockBatch addStock(@RequestParam Long productId,
                               @RequestParam Double quantity,
                               @RequestParam String expiryDate) {
        return stockBatchService.addStock(productId, quantity, LocalDate.parse(expiryDate));
    }

    @PostMapping("/deduct")
    public String deductStock(@RequestParam Long productId,
                              @RequestParam Double quantity) {
        return stockBatchService.deductStock(productId, quantity);
    }

    @GetMapping("/product/{productId}")
    public List<StockBatch> getBatches(@PathVariable Long productId) {
        return stockBatchService.getBatchesForProduct(productId);
    }
}