package com.tradingplatform.repository;

import com.tradingplatform.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepo extends JpaRepository<Coin,String> {
}
