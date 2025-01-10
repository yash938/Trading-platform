package com.tradingplatform.controller;

import com.tradingplatform.entity.*;
import com.tradingplatform.service.OrderService;
import com.tradingplatform.service.PaymentService;
import com.tradingplatform.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {


    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;
    @GetMapping("/wallet-api")
    public ResponseEntity<Wallet> getUserWallet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Wallet userWallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(userWallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@PathVariable int walletId, @RequestBody WalletTransaction walletReq) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Wallet receiverWallet = walletService.findById(walletId);
        Wallet wallet = walletService.walletToWalletTransaction(user,receiverWallet,walletReq.getAmount());

        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    @PutMapping("/order{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@PathVariable int orderId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order orderById = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(orderById,user);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    @PutMapping("/wallet/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(@RequestParam(name = "order_id") int orderId,@RequestParam(name = "payment_id") int paymentId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Wallet userWallet = walletService.getUserWallet(user);
        PaymentOrder paymentOrder = paymentService.getPaymentOrder(orderId);

        boolean status = paymentService.proceedPaymentOrder(paymentOrder,paymentId);
        if(status){
            userWallet = walletService.addBalanceToWallet(userWallet,paymentOrder.getAmount())
;        }
        return new ResponseEntity<>(userWallet,HttpStatus.OK);
    }



}
