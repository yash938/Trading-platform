package com.tradingplatform.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        log.info("Authorization header {}",authorization);

        String username = null;
        String token = null;

        if(authorization != null && authorization.startsWith("Bearer")){
               token= authorization.substring(7);

               try{
                   username= helper.getUsernameFromToken(token);
                    log.info("Username of token {}",username);

               }catch (IllegalArgumentException e) {
                   e.printStackTrace();
                   logger.info("illegal argument while fetching the argument " + e.getMessage());
               }
               catch (ExpiredJwtException ex){
                   logger.info("Given JWT is Expired " + ex.getMessage());
               }
               catch (MalformedJwtException ex){
                   logger.info("Some changed has done in token "+ex.getMessage());
               }
               catch (Exception e){

               }
        }else {
            log.error("Invalid Header..............");
        }


        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(username.equals(userDetails.getUsername()) && !helper.isTokenExpired(token)){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        }
            filterChain.doFilter(request,response);
    }
}
























