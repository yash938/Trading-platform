package com.tradingplatform.repository;

import com.tradingplatform.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Integer> {
    Wallet findByUserId(int userId);
}
