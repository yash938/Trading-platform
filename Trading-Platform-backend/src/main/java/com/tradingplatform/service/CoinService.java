package com.tradingplatform.service;

import com.tradingplatform.entity.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page) throws Exception;
    String getMarketChart(String coinId,int days) throws Exception;
    String getCoinDetails(String coinId) throws Exception;
    Coin findById(String  id);
    String searchCoin(String keyword) throws Exception;
    String getTop50coinByMarketCapRank() throws Exception;
    String getTreadingCoins() throws Exception;

}
