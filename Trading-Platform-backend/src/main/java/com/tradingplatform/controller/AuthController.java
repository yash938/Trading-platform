package com.tradingplatform.controller;

import com.tradingplatform.dto.*;
import com.tradingplatform.entity.User;
import com.tradingplatform.security.JwtHelper;
import com.tradingplatform.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        log.info("Username {} , Password {} ",jwtRequest.getEmail(),jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());

        User user = (User)userDetailsService.loadUserByUsername(jwtRequest.getEmail());


        String token = jwtHelper.generateToken( user);
        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());




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
}
