package com.tradingplatform.security;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User is not found with given id"));
        return user;
    }
}
