package com.tradingplatform.repository;

import com.tradingplatform.entity.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorRepo extends JpaRepository<TwoFactorOtp,String> {
    TwoFactorOtp findByUserId(int userId);
}
