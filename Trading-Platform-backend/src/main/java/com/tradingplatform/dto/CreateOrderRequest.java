package com.tradingplatform.dto;

import com.tradingplatform.domain.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest  {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
