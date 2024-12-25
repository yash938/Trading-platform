package com.tradingplatform.service;

import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.VerificationCode;

public interface VerificationService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(int  id);

    VerificationCode getVerificationByUser(int userId);
    void verificationCodeDelete(int id);
}
