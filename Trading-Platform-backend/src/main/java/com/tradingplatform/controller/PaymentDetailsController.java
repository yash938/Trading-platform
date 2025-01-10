package com.tradingplatform.controller;

import com.tradingplatform.entity.PaymentDetails;
import com.tradingplatform.entity.User;
import com.tradingplatform.service.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentDetailsController {
    @Autowired
     private PaymentDetailsService paymentDetailsService;

    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsReq
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
                paymentDetailsReq.getAmountNumber(),
                paymentDetailsReq.getAccountHolderName(),
                paymentDetailsReq.getIfscCode(),
                paymentDetailsReq.getBankName(), user
        );

        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }


    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails>  getUserPAymentDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        PaymentDetails userPamentDetails = paymentDetailsService.getUserPamentDetails(user);
        return new ResponseEntity<>(userPamentDetails,HttpStatus.OK);
    }
}
