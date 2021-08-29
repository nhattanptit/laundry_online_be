package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.models.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {
}
