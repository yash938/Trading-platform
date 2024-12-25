package com.tradingplatform.service;


import com.tradingplatform.dto.RefreshTokenDto;
import com.tradingplatform.dto.UserDto;

public interface RefreshTokenService {

     public RefreshTokenDto createRefreshToken(String username);

     public RefreshTokenDto findByToken(String token);

     public RefreshTokenDto verifyToken(RefreshTokenDto refreshTokenDto);

     UserDto getUser(RefreshTokenDto tokenDto);

}
