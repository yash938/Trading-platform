package com.tradingplatform.service;

import com.tradingplatform.entity.User;
import com.tradingplatform.entity.Withdrawl;


import java.util.List;

public interface WithdrawlService {
    Withdrawl requestWithdrawl(int amount, User user);
    Withdrawl proceedWithdrawl(int withdrawlId,boolean accept);
    List<Withdrawl> getUserWithdrawlHistor(User user);
    List<Withdrawl> getAllWithdrawlRequest();
}
