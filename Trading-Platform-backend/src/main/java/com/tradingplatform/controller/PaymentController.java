package com.tradingplatform.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradingplatform.domain.PaymentMethod;
import com.tradingplatform.dto.PaymentResponse;
import com.tradingplatform.entity.PaymentOrder;
import com.tradingplatform.entity.User;
import com.tradingplatform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod,@PathVariable long amount) throws RazorpayException, StripeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        PaymentResponse paymentResponse;

        PaymentOrder createorder = paymentService.createorder(user, amount, paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorPAyPAymentLink(user,amount);
        }else{
            paymentResponse=paymentService.createStripePAymentLink(user,amount, createorder.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}

