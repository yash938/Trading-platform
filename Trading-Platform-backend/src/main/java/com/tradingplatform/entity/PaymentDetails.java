package com.tradingplatform.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String amountNumber;
    private String accountHolderName;

    private String ifscCode;

    private String bankName;
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

}
