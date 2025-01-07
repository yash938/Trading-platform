package com.tradingplatform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
