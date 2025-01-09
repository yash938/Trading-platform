package com.tradingplatform.controller;

import com.tradingplatform.entity.Asset;
import com.tradingplatform.entity.User;
import com.tradingplatform.service.AssetService;
import com.tradingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable int assetId){
        Asset assetById = assetService.getAssetById(assetId);
        return new ResponseEntity<>(assetById, HttpStatus.OK);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIDAndCoinId(@PathVariable String coindId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coindId);
        return new ResponseEntity<>(asset,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAssetForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Asset> usersAssets = assetService.getUsersAssets(user.getId());
        return new ResponseEntity<>(usersAssets,HttpStatus.OK);
    }
}
