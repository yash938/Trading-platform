package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.domain.OrderType;
import com.tradingplatform.entity.Order;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.Wallet;
import com.tradingplatform.repository.WalletRepo;
import com.tradingplatform.service.OrderService;
import com.tradingplatform.service.UserService;
import com.tradingplatform.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private UserService userService;
//    @Autowired
//    private OrderService orderService;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepo.findByUserId(user.getId());
        if(wallet == null){
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalanceToWallet(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findById(int id) {
        Wallet wallet = walletRepo.findById(id).orElseThrow(() -> new RuntimeException("wallet not found"));
        return wallet;
    }

    @Override
    public Wallet walletToWalletTransaction(User sender, Wallet receiver, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);

        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("InsufficientBalance......");
        }

        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);

        walletRepo.save(senderWallet);
        BigDecimal receiverBalance = receiver.getBalance().add(BigDecimal.valueOf(amount));
        receiver.setBalance(receiverBalance);
        walletRepo.save(receiver);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient funds for this transient");
            }

            wallet.setBalance(newBalance);
        }
        else{
            BigDecimal newBalance  = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }

        walletRepo.save(wallet);
        return wallet;
    }
}
