package com.laundy.laundrybackend.service;

import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.dtos.OrderDetailResponseDTO;
import com.laundy.laundrybackend.models.dtos.OrderResponseDTO;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderPaymentForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.models.response.ServicesFeeResponse;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderDetailResponseDTO createNewOrder(NewOrderForm orderForm);
    List<OrderResponseDTO> getOrderByStatus(OrderStatusEnum status, int page, int size);
    OrderDetailResponseDTO getOrderDetail(Long orderId);
    void cancelOrder(Long orderId);
    void updateOrderPayment(OrderPaymentForm orderPaymentForm);
    BigDecimal getServicesFee(Double distance);
    ServicesFeeResponse getServicesFee(List<OrderServiceDetailForm> detailFormList);
}
