package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.controller.api.OrderInterface;
import com.laundy.laundrybackend.models.ServiceDetail;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.repository.ServiceDetailsRepository;
import com.laundy.laundrybackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController implements OrderInterface {
    private final OrderService orderService;
    private final ServiceDetailsRepository serviceDetailsRepository;

    @Autowired
    public OrderController(OrderService orderService, ServiceDetailsRepository serviceDetailsRepository) {
        this.orderService = orderService;
        this.serviceDetailsRepository = serviceDetailsRepository;
    }

    @Override
    public GeneralResponse<?> createNewOrder(NewOrderForm orderForm) {
        List<Long> ids = orderForm.getOrderServiceDetailForms().stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        return ResponseFactory.sucessRepsonse(orderService.createNewOrder(orderForm));
    }
}
