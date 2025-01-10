package com.tradingplatform.ServiceImpementation;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.domain.PaymentMethod;
import com.tradingplatform.domain.PaymentOrderStatus;
import com.tradingplatform.dto.PaymentResponse;
import com.tradingplatform.entity.PaymentOrder;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.PaymentRepo;
import com.tradingplatform.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    private String stripeSecretKey;

    private String RazorApiKey;

    private String razorPaySecretKey;

    @Override
    public PaymentOrder createorder(User user, long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        return paymentRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrder(int id) {

        return paymentRepo.findById(id).orElseThrow(()->new ResourceNotFound("payment is not found"));
    }

    @Override
    public boolean proceedPaymentOrder(PaymentOrder paymentOrder, int paymentId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpayClient = new RazorpayClient(RazorApiKey,razorPaySecretKey);

                Payment payment = razorpayClient.payments.fetch(String.valueOf(paymentId));
                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }

                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentRepo.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentRepo.save(paymentOrder);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
//        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
//            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
//                RazorpayClient razorpayClient = new RazorpayClient(RazorApiKey,razorPaySecretKey);
//
//                Payment payment = razorpayClient.payments.fetch(paymentId);
//                Integer amount = payment.get("amount");
//                String status = payment.get("status");
//
//                if(status.equals("captured")){
//                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
//                    return true;
//                }
//
//                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
//                paymentRepo.save(paymentOrder);
//                return false;
//            }
//            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
//            paymentRepo.save(paymentOrder);
//            return true;
//        }
//        return false;
//    }

    @Override
    public PaymentResponse createRazorPAyPAymentLink(User user, long amount) throws RazorpayException {
        long Amount = amount * 100;

        try{
            RazorpayClient razorpayClient = new RazorpayClient(RazorApiKey,razorPaySecretKey);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer  = new JSONObject();
            customer.put("name",user.getFullName());

            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);

            paymentLinkRequest.put("callback_url","http://localhost:5173/wallet");
            paymentLinkRequest.put("callback_method","get");
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = paymentLink.get("id");
            String paymentLinkUrl = paymentLink.get("short_url");

            PaymentResponse res = new PaymentResponse();
            res.setPayment_url(paymentLinkUrl);
            return res;
        }catch (RazorpayException e){
            log.error("Error creating payment Link");
            throw new RazorpayException(e.getMessage());
        }

    }

    @Override
    public PaymentResponse createStripePAymentLink(User user, long amount, int orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)  // Payment method type
                .setMode(SessionCreateParams.Mode.PAYMENT)  // Payment mode (payment intent)
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)  // Success URL after payment
                .setCancelUrl("http://localhost:5173/payment/cancel")  // URL for canceling the payment
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)  // Item quantity
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")  // Currency type
                                .setUnitAmount(amount * 100)  // Amount in cents
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Top Up Wallet")  // Item name
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        log.info("session......{}",session);
        PaymentResponse res = new PaymentResponse();
        res.setPayment_url(session.getUrl());

        return res;
    }
}
