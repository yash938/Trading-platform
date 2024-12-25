package com.tradingplatform.repository;

import com.tradingplatform.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepo extends JpaRepository<VerificationCode,Integer> {

    public VerificationCode findByUserId(int userId);

}
