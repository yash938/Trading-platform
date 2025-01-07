package com.tradingplatform.entity;

import com.tradingplatform.domain.VerificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private User user;

    private String otp;
    private VerificationType verificationType;

    private String sendTo;
}
