package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.WatchList;
import com.tradingplatform.repository.WatchListRepo;
import com.tradingplatform.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepo watchListRepo;


    @Override
    public WatchList findUserWatchList(int userid) {
        WatchList watchList = watchListRepo.findByUserId(userid);
        if(watchList == null){
            throw new ResourceNotFound("Watch List not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList  = new WatchList();
        watchList.setUser(user);

        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findByWatchListId(int id) {
        WatchList watchList = watchListRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Watch list not found"));
        return watchList;
    }

    @Override
    public Coin addItemToWatchList(Coin coin, User user) {
        WatchList userWatchList = findUserWatchList(user.getId());
        if(userWatchList.getCoins().contains(coin)){
            userWatchList.getCoins().remove(coin);
        }else userWatchList.getCoins().add(coin);
            watchListRepo.save(userWatchList);
            return coin;
    }
}
