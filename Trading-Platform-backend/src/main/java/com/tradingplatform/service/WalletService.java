package com.tradingplatform.service;

import com.tradingplatform.entity.Order;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalanceToWallet(Wallet wallet,Long money);
    Wallet findById(int id);
    Wallet walletToWalletTransaction(User sender,Wallet receiver,Long amount) throws Exception;
    Wallet payOrderPayment(Order order,User user) throws Exception;

}
