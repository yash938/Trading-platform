package com.tradingplatform.repository;

import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public Optional<UserDto> findByEmail(String email);
}
