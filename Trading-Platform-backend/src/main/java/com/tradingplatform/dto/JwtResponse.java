package com.tradingplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    UserDto user;
    private RefreshTokenDto refreshTokenDto;
    private String message;
    private boolean twoFactorAuthIsEnabled;
    private String session;
}
