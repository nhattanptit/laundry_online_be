package com.laundy.laundrybackend.service.impl;

import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.*;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.repository.*;
import com.laundy.laundrybackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final OrderServiceDetailRepository orderServiceDetailRepository;
    private final OrderRepository orderRepository;
    private final ShipFeeRepository shipFeeRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(ServiceDetailsRepository serviceDetailsRepository, OrderServiceDetailRepository orderServiceDetailRepository, OrderRepository orderRepository, ShipFeeRepository shipFeeRepository, UserRepository userRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.orderServiceDetailRepository = orderServiceDetailRepository;
        this.orderRepository = orderRepository;
        this.shipFeeRepository = shipFeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createNewOrder(NewOrderForm orderForm) {
        List<Long> ids = orderForm.getOrderServiceDetailForms().stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        ShipFee shipFee = shipFeeRepository.getShipFeeByDistance(orderForm.getDistance());
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Order order = Order.builder()
                .distance(orderForm.getDistance())
                .status(OrderStatusEnum.NEW)
                .shipFee(shipFee)
                .shippingAddress(orderForm.getShippingAddress())
                .totalShipFee(shipFee.getFee().multiply(new BigDecimal(orderForm.getDistance())))
                .totalServiceFee(BigDecimal.ONE)
                .user(currentUser)
                .build();
        ServiceDetail serviceDetail = serviceDetailsRepository.getById(1L);
        OrderServiceDetail orderServiceDetail = OrderServiceDetail.builder()
                .order(order)
                .serviceDetail(serviceDetail)
                .quantity(5)
                .build();

        order.setTotalServiceFee(BigDecimal.valueOf(orderServiceDetail.getQuantity()).multiply((serviceDetail.getPrice())));
        order.setTotalBill(order.getTotalServiceFee().add(order.getTotalShipFee()));
        orderServiceDetailRepository.saveAndFlush(orderServiceDetail);
        return order;
    }
}
