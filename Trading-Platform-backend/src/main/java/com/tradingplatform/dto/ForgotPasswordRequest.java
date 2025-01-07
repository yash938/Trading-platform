package com.tradingplatform.dto;

import com.tradingplatform.domain.VerificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    private String sendTo;
    private VerificationType verificationType;

}
