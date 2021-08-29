package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
