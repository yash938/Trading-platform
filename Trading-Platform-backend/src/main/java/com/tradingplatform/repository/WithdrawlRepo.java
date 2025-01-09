package com.tradingplatform.repository;

import com.tradingplatform.entity.Withdrawl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawlRepo extends JpaRepository<Withdrawl,Integer> {
    List<Withdrawl> findByUserId(int userId);
}
