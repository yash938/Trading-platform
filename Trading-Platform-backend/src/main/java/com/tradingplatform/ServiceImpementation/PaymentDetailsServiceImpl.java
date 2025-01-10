package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.entity.PaymentDetails;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.PaymentDetailsRepo;
import com.tradingplatform.service.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;


    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolder, String ifscCode, String bankName, User user) {
         PaymentDetails paymentDetails = new PaymentDetails();
         paymentDetails.setAccountHolderName(accountHolder);
         paymentDetails.setAmountNumber(accountNumber);
         paymentDetails.setBankName(bankName);
         paymentDetails.setUser(user);
        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPamentDetails(User user) {
        return paymentDetailsRepo.findByUserId(user.getId());
    }
}
