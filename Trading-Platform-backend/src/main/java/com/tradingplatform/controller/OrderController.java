package com.tradingplatform.controller;

import com.tradingplatform.domain.OrderType;
import com.tradingplatform.dto.CreateOrderRequest;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.Order;
import com.tradingplatform.entity.User;
import com.tradingplatform.service.CoinService;
import com.tradingplatform.service.OrderService;
import com.tradingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PutMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestBody CreateOrderRequest req) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId() == user.getId()){
            return new ResponseEntity<>(order,HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    public ResponseEntity<List<Order>> getAllOrdersForUsers(
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    )
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int id = user.getId();

        List<Order> allOrder = orderService.getAllOrder(id, order_type, asset_symbol);
        return new ResponseEntity<>(allOrder,HttpStatus.ACCEPTED);
    }


    //6 : 02
}
