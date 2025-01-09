package com.tradingplatform.service;

import com.tradingplatform.domain.OrderType;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.Order;
import com.tradingplatform.entity.OrderItem;
import com.tradingplatform.entity.User;

import java.util.List;

public interface  OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(int orderId);
    List<Order> getAllOrder(int userId,OrderType orderType,String assetSymbol);
    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
}
