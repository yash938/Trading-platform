package com.tradingplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double quantity;

    @ManyToMany
    private Coin coin;

    private double buyPrice;
    private double sellPrice;
    @JsonIgnore
    @OneToOne
    private Order order;



}
