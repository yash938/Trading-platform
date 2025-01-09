package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.domain.WithDrawlStatus;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.Withdrawl;
import com.tradingplatform.repository.WithdrawlRepo;
import com.tradingplatform.service.WithdrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WithdrawlServiceImpl implements WithdrawlService {

    @Autowired
    private WithdrawlRepo withdrawlRepo;

    @Override
    public Withdrawl requestWithdrawl(int amount, User user) {
        Withdrawl withdrawl = new Withdrawl();
        withdrawl.setAmount(amount);
        withdrawl.setUser(user);
        withdrawl.setWithDrawlStatus(WithDrawlStatus.PENDING);
        return withdrawlRepo.save(withdrawl);
    }

    @Override
    public Withdrawl proceedWithdrawl(int withdrawlId, boolean accept) {
        Withdrawl withdrawl = withdrawlRepo.findById(withdrawlId).orElseThrow(() -> new ResourceNotFound("Withdrawl id is not found"));

        withdrawl.setDateTime(LocalDateTime.now());

        if(accept ==true){
            withdrawl.setWithDrawlStatus(WithDrawlStatus.SUCCESS);
        }else{
            withdrawl.setWithDrawlStatus(WithDrawlStatus.DECLINE);
        }
        return withdrawlRepo.save(withdrawl);
    }

    @Override
    public List<Withdrawl> getUserWithdrawlHistor(User user) {

        return withdrawlRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawl> getAllWithdrawlRequest() {
        return withdrawlRepo.findAll();
    }
}
