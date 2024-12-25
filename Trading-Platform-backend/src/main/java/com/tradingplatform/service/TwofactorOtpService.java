package com.tradingplatform.service;

import com.tradingplatform.entity.TwoFactorOtp;
import com.tradingplatform.entity.User;

public interface TwofactorOtpService {

    TwoFactorOtp createTwoFactorOtp(User user,String otp,String jwt);
    TwoFactorOtp findUser(int id);
    TwoFactorOtp findId(String id);
    boolean verifyTwoFctorOtp(TwoFactorOtp twoFactorOtp,String otp);

    void deleteOtp(TwoFactorOtp twoFactorOtp);
}
