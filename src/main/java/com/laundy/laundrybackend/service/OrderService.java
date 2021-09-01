package com.laundy.laundrybackend.service;

import com.laundy.laundrybackend.models.Order;
import com.laundy.laundrybackend.models.request.NewOrderForm;

public interface OrderService {
    Order createNewOrder(NewOrderForm orderForm);
}
