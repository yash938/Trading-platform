package com.tradingplatform.repository;

import com.tradingplatform.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepo extends JpaRepository<WatchList,Integer> {
    WatchList findByUserId(int userId);
}
