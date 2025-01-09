package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.domain.OrderStatus;
import com.tradingplatform.domain.OrderType;
import com.tradingplatform.entity.*;
import com.tradingplatform.repository.OrderItemRepo;
import com.tradingplatform.repository.OrderRepo;
import com.tradingplatform.service.AssetService;
import com.tradingplatform.service.OrderService;
import com.tradingplatform.service.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AssetService assetService;

    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WalletService walletService;
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price  = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResolutionException("Order is not found"));
        return order;
    }

    @Override
    public List<Order> getAllOrder(int userId, OrderType orderType, String assetSymbol) {
        List<Order> byUserId = orderRepo.findByUserId(userId);
        return byUserId;
    }

    private OrderItem createOrdeItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity <= 0 ){
            throw new RuntimeException("Quantity should be greater than 0");
        }

        double currentPrice = coin.getCurrentPrice();
        OrderItem ordeItem = createOrdeItem(coin, quantity, currentPrice, 0);

        Order order = createOrder(user, ordeItem, OrderType.BUY);
        ordeItem.setOrder(order);

        walletService.payOrderPayment(order,user);

        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order saveOrder = orderRepo.save(order);

        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),order.getOrderItem().getCoin().getId());

        if(oldAsset == null){
            assetService.createAsset(user,ordeItem.getCoin(), ordeItem.getQuantity());
        }else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return saveOrder;
    }

    @Transactional
    public Order sellAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity <= 0 ){
            throw new RuntimeException("Quantity should be greater than 0");
        }

        double sellPrice = coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(),coin.getId());

        double buyPrice = assetToSell.getBuyPrice();

        if(assetToSell != null) {
            OrderItem ordeItem = createOrdeItem(coin, quantity, buyPrice, sellPrice);

            Order order = createOrder(user, ordeItem, OrderType.SELL);
            ordeItem.setOrder(order);

            if (assetToSell.getQuantity() >= quantity) {
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order saveOrder = orderRepo.save(order);
                walletService.payOrderPayment(order, user);

                Asset updateAsset = assetService.updateAsset(assetToSell.getId(),-quantity);

                if (updateAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updateAsset.getId());
                }
                return saveOrder;
            }

            throw new Exception("Insufficient quantity to sell");
        }
        throw new Exception("asset not found");

    }


    @Transactional
    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,user);
        } else if (orderType.equals(OrderType.SELL)) {
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("Invalid order type");
    }
}
