package com.laundy.laundrybackend.service.impl;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.*;
import com.laundy.laundrybackend.models.dtos.OrderResponseDTO;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.repository.*;
import com.laundy.laundrybackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final OrderServiceDetailRepository orderServiceDetailRepository;
    private final OrderRepository orderRepository;
    private final ShipFeeRepository shipFeeRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public OrderServiceImpl(ServiceDetailsRepository serviceDetailsRepository, OrderServiceDetailRepository orderServiceDetailRepository, OrderRepository orderRepository, ShipFeeRepository shipFeeRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.orderServiceDetailRepository = orderServiceDetailRepository;
        this.orderRepository = orderRepository;
        this.shipFeeRepository = shipFeeRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createNewOrder(NewOrderForm orderForm) {
        ShipFee shipFee = shipFeeRepository.getShipFeeByDistance(orderForm.getDistance());
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Order order = Order.builder()
                .distance(orderForm.getDistance())
                .status(OrderStatusEnum.NEW)
                .shipFee(shipFee)
                .shippingAddress(orderForm.getShippingAddress())
                .totalShipFee(shipFee.getFee().multiply(new BigDecimal(orderForm.getDistance())))
                .user(currentUser)
                .build();
        com.laundy.laundrybackend.models.Service service = serviceRepository.getById(orderForm.getServiceId());
        orderServiceDetailRepository.saveAllAndFlush(orderServiceDetailsFromNewOrderForm(orderForm,order));
        return OrderResponseDTO.orderResponseDTOFromOrderAndService(order,service);
    }

    private List<OrderServiceDetail> orderServiceDetailsFromNewOrderForm(NewOrderForm orderForm, Order order){
        List<Long> ids = orderForm.getOrderServiceDetailForms().stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        List<ServiceDetail> serviceDetails = serviceDetailsRepository.findAllById(ids);
        if (serviceDetails.isEmpty()) throw new NoResultException("NO SERVICE DETAIL");
        List<OrderServiceDetail> orderServiceDetails = new ArrayList<>();
        for (OrderServiceDetailForm serviceDetailForm: orderForm.getOrderServiceDetailForms()){
            ServiceDetail serviceDetail = serviceDetails.stream()
                    .filter(detail -> serviceDetailForm.getServiceDetailId().equals(detail.getId()))
                    .findAny()
                    .orElse(null);

            orderServiceDetails.add(OrderServiceDetail.builder()
                    .order(order)
                    .serviceDetail(serviceDetail)
                    .quantity(serviceDetailForm.getQuantity())
                    .build());
            order.setTotalServiceFee(BigDecimal.valueOf(serviceDetailForm.getQuantity()).multiply((serviceDetail.getPrice())));
        }
        order.setTotalBill((order.getTotalServiceFee().add(order.getTotalShipFee())).multiply(Constants.VAT_VALUES));
        return orderServiceDetails;
    }

}
