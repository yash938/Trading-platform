package com.tradingplatform.repository;

import com.tradingplatform.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepo extends JpaRepository<ResetPassword,Integer> {
    ResetPassword findByUserId(int userId);
}
