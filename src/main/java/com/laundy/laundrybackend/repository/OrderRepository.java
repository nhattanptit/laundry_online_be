package com.laundy.laundrybackend.repository;

import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select o from Order o join User u on o.user.id = u.id where o.user.username = :username and (:status is null or o.status = :status)")
    List<Order> getUserOrderByStatusAndUsername(OrderStatusEnum status, String username, Pageable pageable);

    @Query(value = "update Order o set o.status = :status where o.id = :id")
    Boolean updateOrderStatus(Long id, OrderStatusEnum status);
}
