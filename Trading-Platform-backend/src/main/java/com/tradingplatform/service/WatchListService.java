package com.tradingplatform.service;

import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(int userid);
    WatchList createWatchList(User user);
    WatchList findByWatchListId(int id);
    Coin addItemToWatchList(Coin coin,User user);

}
