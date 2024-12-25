package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.VerificationCode;
import com.tradingplatform.repository.VerificationRepo;
import com.tradingplatform.service.VerificationService;
import com.tradingplatform.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeImpl implements VerificationService {
    @Autowired
    private VerificationRepo verificationRepo;
    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(int id) {
        VerificationCode verifyCode = verificationRepo.findById(id).orElseThrow(() -> new ResourceNotFound("verification code is not found"));
        return verifyCode;
    }

    @Override
    public VerificationCode getVerificationByUser(int userId) {
        VerificationCode user = verificationRepo.findByUserId(userId);
        return user;
    }

    @Override
    public void verificationCodeDelete(int id) {
        VerificationCode verificationCodeById = getVerificationCodeById(id);
        verificationRepo.delete(verificationCodeById);
    }
}
