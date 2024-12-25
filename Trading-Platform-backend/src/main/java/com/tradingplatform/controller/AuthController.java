package com.tradingplatform.controller;

import com.tradingplatform.dto.*;
import com.tradingplatform.entity.TwoFactorOtp;
import com.tradingplatform.entity.User;
import com.tradingplatform.security.JwtHelper;
import com.tradingplatform.service.EmailService;
import com.tradingplatform.service.RefreshTokenService;
import com.tradingplatform.service.TwofactorOtpService;
import com.tradingplatform.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TwofactorOtpService twofactorOtpService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws MessagingException {
        log.info("Username {} , Password {} ",jwtRequest.getEmail(),jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());

        User user = (User)userDetailsService.loadUserByUsername(jwtRequest.getEmail());


        String token = jwtHelper.generateToken( user);
        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());


        //Two Factor otp Service
        if(user.getTwoFactorAuth().isEnabled()){
//            JwtResponse twoFactorAuth = JwtResponse.builder().message("Two Factor auth is enabled").twoFactorAuthIsEnabled(true).build();
            String otp = OtpUtils.generateOtp();

            TwoFactorOtp oldTwoFactorOtp = twofactorOtpService.findUser(user.getId());
            if(oldTwoFactorOtp != null){
                twofactorOtpService.deleteOtp(oldTwoFactorOtp);
            }

            twofactorOtpService.createTwoFactorOtp(user,otp,token);
            //send email
            emailService.sendVerification(user.getEmail(),otp);

            JwtResponse res = JwtResponse.builder().message("Two factor auth is enabled").twoFactorAuthIsEnabled(true).session(new TwoFactorOtp().getId()).build();
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);


        }

        JwtResponse build = JwtResponse.builder().token(token).refreshTokenDto(refreshToken).user(modelMapper.map(user, UserDto.class)).build();
        return ResponseEntity.ok(build);
    }


    @GetMapping("/refreshToken")
    public ResponseEntity<JwtResponse> generateRefreshToken(@RequestBody RefreshToken refreshToken){
        RefreshTokenDto byToken = refreshTokenService.findByToken(refreshToken.getRefreshToken());
        RefreshTokenDto refreshTokenDto = refreshTokenService.verifyToken(byToken);


        UserDto user = refreshTokenService.getUser(refreshTokenDto);
        String s = jwtHelper.generateToken(modelMapper.map(user,User.class));

        JwtResponse build = JwtResponse.builder().token(s).refreshTokenDto(byToken).user(user ).build();
        return ResponseEntity.ok(build);

    }

    private void doAuthenticate(String email, String password) {
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        }catch (BadCredentialsException ex){
            ex.getMessage();
        }
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<JwtResponse> verifySignOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
        // Find the TwoFactorOtp object using the id
        TwoFactorOtp twoFactorOtp = twofactorOtpService.findId(id);

        // Verify the OTP
        if (twofactorOtpService.verifyTwoFctorOtp(twoFactorOtp, otp)) {
            JwtResponse twoFactor = JwtResponse.builder()
                    .message("Two-factor authentication verified")
                    .twoFactorAuthIsEnabled(true)
                    .token(twoFactorOtp.getJwt())
                    .build();

            return new ResponseEntity<>(twoFactor, HttpStatus.OK);
        }

        // Throw an exception if OTP is invalid
        throw new Exception("Invalid OTP");
    }

}
