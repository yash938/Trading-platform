package com.tradingplatform.entity;

import com.tradingplatform.domain.VerificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String otp;

    @OneToOne
    private User user;
    private String email;
    private String mobile;
    private VerificationType verificationType;
}
