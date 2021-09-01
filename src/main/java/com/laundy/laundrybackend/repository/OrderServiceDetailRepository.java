package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.models.OrderServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderServiceDetailRepository extends JpaRepository<OrderServiceDetail,Long> {
}
