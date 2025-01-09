package com.tradingplatform.service;

import com.tradingplatform.entity.Asset;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin,double quantity);
    Asset getAssetById(int id);
    Asset getAssetByUserIdAndId(int userId,int assetId);
    List<Asset> getUsersAssets(int userId);
    Asset updateAsset(int id,double quantity);
    Asset findAssetByUserIdAndCoinId(int userId,String coinId);
    void deleteAsset(int id);
}
