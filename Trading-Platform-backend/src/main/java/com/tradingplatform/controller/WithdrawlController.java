package com.tradingplatform.controller;

import com.tradingplatform.entity.User;
import com.tradingplatform.entity.Wallet;
import com.tradingplatform.entity.Withdrawl;
import com.tradingplatform.service.WalletService;
import com.tradingplatform.service.WithdrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/withdrawl")
public class WithdrawlController {

    @Autowired
    private WithdrawlService withdrawlService;

    @Autowired
    private WalletService walletService;

    @PostMapping("/{amount}")
     public ResponseEntity<?> withDrawlRequest(@PathVariable int amount){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) authentication.getPrincipal();

         Wallet userWallet = walletService.getUserWallet(user);

         Withdrawl withdrawl = withdrawlService.requestWithdrawl(amount, user);
         walletService.addBalanceToWallet(userWallet,-withdrawl.getAmount());

         return new ResponseEntity<>(withdrawl, HttpStatus.OK);
     }


     @PutMapping("/admin/{id}/proceed/{accept}")
    public ResponseEntity<?> processWIthdrawl(@PathVariable boolean accept,
                                              @PathVariable int id){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) authentication.getPrincipal();

         Withdrawl withdrawl = withdrawlService.proceedWithdrawl(id, accept);
         Wallet userWallet = walletService.getUserWallet(user);

         if(!accept){
             walletService.addBalanceToWallet(userWallet,withdrawl.getAmount());
         }
         return new ResponseEntity<>(withdrawl,HttpStatus.OK);
     }

     @GetMapping
    public ResponseEntity<List<Withdrawl>> getWithdrwlHistory(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) authentication.getPrincipal();

         List<Withdrawl> userWithdrawlHistor = withdrawlService.getUserWithdrawlHistor(user);
         return new ResponseEntity<>(userWithdrawlHistor,HttpStatus.OK);
     }

     @GetMapping("/admin/withdrawl")
    public ResponseEntity<List<Withdrawl>> getAllWihdrawlRequest(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) authentication.getPrincipal();

         List<Withdrawl> allWithdrawlRequest = withdrawlService.getAllWithdrawlRequest();

         return new ResponseEntity<>(allWithdrawlRequest,HttpStatus.OK);
     }
}
