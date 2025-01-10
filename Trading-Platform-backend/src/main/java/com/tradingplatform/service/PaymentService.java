package com.tradingplatform.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradingplatform.domain.PaymentMethod;
import com.tradingplatform.dto.PaymentResponse;
import com.tradingplatform.entity.PaymentOrder;
import com.tradingplatform.entity.User;

public interface PaymentService {

    PaymentOrder createorder(User user, long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrder(int id);
    boolean proceedPaymentOrder(PaymentOrder paymentOrder,int paymentId) throws RazorpayException;
    PaymentResponse createRazorPAyPAymentLink(User user, long amount) throws RazorpayException;

    PaymentResponse createStripePAymentLink(User user, long amount,int orderId) throws StripeException;

}

