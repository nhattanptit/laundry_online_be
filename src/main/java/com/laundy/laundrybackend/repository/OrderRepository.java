package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
