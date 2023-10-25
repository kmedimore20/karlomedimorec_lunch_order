package com.example.lunchorder_v2.repository;

import com.example.lunchorder_v2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
