package com.tradingplatform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coin {
    @Id
    private String id;
    private String symbol;
    private String name;
    private String image;
    private double currentPrice;
    private long marketCap;
    private int marketCapRank;
    private long fullyDilutedValuation;
    private long totalVolume;
    private double high24h;
    private double low24h;
    private double priceChange24h;
    private double priceChangePercentage24h;
    private double marketCapChange24h;
    private double marketCapChangePercentage24h;
    private double circulatingSupply;
    private double totalSupply;
    private double maxSupply;
    private double ath;  // All Time High
    private double athChangePercentage;
    private LocalDateTime athDate;
    private double atl;  // All Time Low
    private double atlChangePercentage;
    private LocalDateTime atlDate;
    private Double roi;  // Return on Investment
    private LocalDateTime lastUpdated;
}
