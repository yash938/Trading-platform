package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.entity.TwoFactorOtp;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.TwoFactorRepo;
import com.tradingplatform.repository.UserRepo;
import com.tradingplatform.service.TwofactorOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwofactorOtpService {

    @Autowired
    private TwoFactorRepo twoFactorRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public TwoFactorOtp createTwoFactorOtp(User user, String otp, String jwt) {
        String uuid = UUID.randomUUID().toString();
        TwoFactorOtp twoFactorOtp = new TwoFactorOtp();
        twoFactorOtp.setId(uuid);
        twoFactorOtp.setUser(user);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setOtp(otp);

        return  twoFactorRepo.save(twoFactorOtp);
    }



    @Override
    public TwoFactorOtp findUser(int id) {
       return twoFactorRepo.findByUserId(id);
    }

    @Override
    public TwoFactorOtp findId(String id) {
        TwoFactorOtp twoFactorOtp = twoFactorRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Not found otp......"));
        return twoFactorOtp;
    }

    @Override
    public boolean verifyTwoFctorOtp(TwoFactorOtp twoFactorOtp, String otp) {
      return   twoFactorOtp.getOtp().equals(otp);

    }

    @Override
    public void deleteOtp(TwoFactorOtp twoFactorOtp) {
            twoFactorRepo.delete(twoFactorOtp);
    }
}
