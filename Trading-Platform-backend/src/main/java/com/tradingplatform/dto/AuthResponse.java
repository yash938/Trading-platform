package com.tradingplatform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private int session;
    private String message;
}
