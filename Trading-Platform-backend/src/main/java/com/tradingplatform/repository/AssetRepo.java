package com.tradingplatform.repository;

import com.tradingplatform.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset,Integer > {
    List<Asset> findByUserId(int userId);
    Asset findByUserIdAndCoinId(int userId,String coinId);
}
