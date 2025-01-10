package com.tradingplatform.service;

import com.tradingplatform.entity.PaymentDetails;
import com.tradingplatform.entity.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolder, String ifscCode, String bankName, User user);

    public PaymentDetails getUserPamentDetails(User user);
}
