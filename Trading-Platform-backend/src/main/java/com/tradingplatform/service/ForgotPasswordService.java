package com.tradingplatform.service;

import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.entity.ResetPassword;
import com.tradingplatform.entity.User;

public interface ForgotPasswordService {
    ResetPassword createToken(User user, String id, String otp, VerificationType verificationType,String sendTo);
    ResetPassword findById(int id);
    ResetPassword findByUser(int userId);
    void deleteToken(ResetPassword resetPassword);
}
