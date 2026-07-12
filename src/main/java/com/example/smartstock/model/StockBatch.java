package com.example.smartstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "stock_batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double quantity;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "added_date")
    private LocalDate addedDate;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    public enum BatchStatus {
        ACTIVE, EXPIRED, DEPLETED
    }
}