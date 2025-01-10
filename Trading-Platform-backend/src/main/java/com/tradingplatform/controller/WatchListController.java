package com.tradingplatform.controller;

import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.WatchList;
import com.tradingplatform.service.CoinService;
import com.tradingplatform.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchList")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        WatchList userWatchList = watchListService.findUserWatchList(user.getId());
        return new ResponseEntity<>(userWatchList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<WatchList> createWatchList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        WatchList watchList = watchListService.createWatchList(user);
        return new ResponseEntity<>(watchList,HttpStatus.CREATED);
    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable int watchListId){
        WatchList byWatchListId = watchListService.findByWatchListId(watchListId);
        return new ResponseEntity<>(byWatchListId,HttpStatus.OK);
    }

    @PutMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@PathVariable String coinId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Coin coin = coinService.findById(coinId);
        Coin coin1 = watchListService.addItemToWatchList(coin, user);
        return new ResponseEntity<>(coin1,HttpStatus.OK);
    }

}
