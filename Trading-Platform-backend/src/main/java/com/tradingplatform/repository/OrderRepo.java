package com.tradingplatform.repository;

import com.tradingplatform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
   List<Order> findByUserId(int userId);
}
