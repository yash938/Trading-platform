package com.tradingplatform.entity;

import com.tradingplatform.domain.WithDrawlStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private WithDrawlStatus withDrawlStatus;
    private long amount;
    @ManyToOne
    private User user;

    private LocalDateTime dateTime = LocalDateTime.now();
}
