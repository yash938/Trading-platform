package com.tradingplatform.ServiceImpementation;


import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.dto.RefreshTokenDto;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.RefreshToken;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.RefreshTokenRepo;
import com.tradingplatform.repository.UserRepo;
import com.tradingplatform.service.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service

public class RefreshTokenServiceImp implements RefreshTokenService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RefreshTokenDto createRefreshToken(String username) {
            User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            RefreshToken refreshToken = refreshTokenRepo.findByUser(user).orElse(null);
            if(refreshToken == null){
                refreshToken = RefreshToken.builder().token(UUID.randomUUID().toString()).user(user).expiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60)).build();
            }else{
                refreshToken.setToken(UUID.randomUUID().toString());
                refreshToken.setExpiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60));
            }


            RefreshToken saveToken = refreshTokenRepo.save(refreshToken);

            return modelMapper.map(saveToken,RefreshTokenDto.class);
    }

    @Override
    public RefreshTokenDto findByToken(String token) {
        RefreshToken refreshTokenIsNotFound = refreshTokenRepo.findByToken(token).orElseThrow(() -> new ResourceNotFound("Refresh token is not found"));
        return modelMapper.map(refreshTokenIsNotFound,RefreshTokenDto.class);
    }

    @Override
    public RefreshTokenDto verifyToken(RefreshTokenDto refreshTokenDto) {

      var  refreshToken = modelMapper.map(refreshTokenDto,RefreshToken.class);

        if(refreshTokenDto.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired!!");
        }
        return refreshTokenDto;
    }

    @Override
    public UserDto getUser(RefreshTokenDto tokenDto) {
        RefreshToken tokenIsNotFound = refreshTokenRepo.findByToken(tokenDto.getToken()).orElseThrow(() -> new ResourceNotFound("Token is not found"));
        User user = tokenIsNotFound.getUser();
        return modelMapper.map(user, UserDto.class);
    }


}
