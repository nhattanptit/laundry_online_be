package com.laundy.laundrybackend.service;

import com.laundy.laundrybackend.models.Order;
import com.laundy.laundrybackend.models.dtos.OrderResponseDTO;
import com.laundy.laundrybackend.models.request.NewOrderForm;

public interface OrderService {
    OrderResponseDTO createNewOrder(NewOrderForm orderForm);
}
