package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.entity.Asset;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.AssetRepo;
import com.tradingplatform.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepo assetRepo;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset() ;
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepo.save(asset);
    }

    @Override
    public Asset getAssetById(int id) {
        Asset asset = assetRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Asset not found"));
        return asset;
    }

    @Override
    public Asset getAssetByUserIdAndId(int userId, int assetId) {

        return null;
    }

    @Override
    public List<Asset> getUsersAssets(int userId) {
        return assetRepo.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(int id, double quantity) {
        Asset oldAsset = getAssetById(id);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());
        return assetRepo.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(int userId, String coinId) {
        return assetRepo.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(int id) {
        Asset assetById = getAssetById(id);
        assetRepo.delete(assetById);
    }
}
