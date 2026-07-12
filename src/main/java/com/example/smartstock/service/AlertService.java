package com.example.smartstock.service;

import com.example.smartstock.model.Alert;
import com.example.smartstock.model.StockBatch;
import com.example.smartstock.repository.AlertRepository;
import com.example.smartstock.repository.StockBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private StockBatchRepository stockBatchRepository;

    @Autowired
    private AlertRepository alertRepository;

    // Runs once a day at 8:00 AM. Cron format: sec min hour day month weekday
    @Scheduled(cron = "0 0 8 * * *")
    public void checkExpiringBatches() {
        List<StockBatch> activeBatches = stockBatchRepository.findAll().stream()
                .filter(b -> b.getStatus() == StockBatch.BatchStatus.ACTIVE)
                .toList();

        LocalDate today = LocalDate.now();
        LocalDate warningWindow = today.plusDays(3); // flag anything expiring within 3 days

        for (StockBatch batch : activeBatches) {
            if (!batch.getExpiryDate().isAfter(warningWindow)) {
                Alert alert = new Alert();
                alert.setProduct(batch.getProduct());
                alert.setBatch(batch);
                alert.setType(Alert.AlertType.EXPIRY_SOON);
                alert.setMessage(batch.getProduct().getName() + " batch expiring on " + batch.getExpiryDate());
                alert.setResolved(false);
                alert.setCreatedAt(LocalDateTime.now());
                alertRepository.save(alert);
            }
        }
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByResolvedFalse();
    }

    // Manually trigger the check (useful for testing without waiting until 8 AM)
    public String runCheckManually() {
        checkExpiringBatches();
        return "Expiry check completed.";
    }
}