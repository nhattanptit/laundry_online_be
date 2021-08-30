package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.models.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffUserRepository extends JpaRepository<StaffUser,Long> {
    Optional<StaffUser> findByUsername(String username);
}
