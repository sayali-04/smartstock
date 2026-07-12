package com.example.smartstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private StockBatch batch;  // nullable — only set for expiry alerts, not low-stock alerts

    @Enumerated(EnumType.STRING)
    private AlertType type;

    private String message;

    private Boolean resolved = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum AlertType {
        EXPIRY_SOON, LOW_STOCK
    }
}