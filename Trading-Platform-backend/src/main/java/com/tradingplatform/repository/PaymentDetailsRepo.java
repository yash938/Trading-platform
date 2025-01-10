package com.tradingplatform.repository;

import com.tradingplatform.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Integer> {

    PaymentDetails findByUserId(int userId);
}
