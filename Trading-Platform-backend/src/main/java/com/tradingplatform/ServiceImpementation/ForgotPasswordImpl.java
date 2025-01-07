package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.dto.HandlerDto;
import com.tradingplatform.entity.ResetPassword;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.ForgotPasswordRepo;
import com.tradingplatform.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ResetPassword createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setOtp(otp);
        resetPassword.setSendTo(sendTo);
        resetPassword.setUser(user);
        resetPassword.setVerificationType(verificationType);
        return forgotPasswordRepo.save(resetPassword);
    }

    @Override
    public ResetPassword findById(int id) {
        ResetPassword resetPassword = forgotPasswordRepo.findById(id).orElseThrow(() -> new ResourceNotFound("reset id is not found"));
        return resetPassword;
    }

    @Override
    public ResetPassword findByUser(int userId) {
        ResetPassword byUserId = forgotPasswordRepo.findByUserId(userId);
        return byUserId;
    }

    @Override
    public void deleteToken(ResetPassword resetPassword) {
            forgotPasswordRepo.delete(resetPassword);
    }
}
