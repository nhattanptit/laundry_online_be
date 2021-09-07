package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.controller.api.OrderInterface;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderPaymentForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController implements OrderInterface {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public GeneralResponse<?> createNewOrder(NewOrderForm orderForm) {
        List<Long> ids = orderForm.getOrderServiceDetails().stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        return ResponseFactory.sucessRepsonse(orderService.createNewOrder(orderForm));
    }

    @Override
    public GeneralResponse<?> getOrderByStatus(String orderStatus, int page, int size) {
        OrderStatusEnum status = orderStatus == null? null: OrderStatusEnum.valueOf(orderStatus);
        return ResponseFactory.sucessRepsonse(orderService.getOrderByStatus(status,page,size));
    }

    @Override
    public GeneralResponse<?> getOrderDetail(Long orderId) {
        return ResponseFactory.sucessRepsonse(orderService.getOrderDetail(orderId));
    }

    @Override
    public GeneralResponse<?> cancelOrder(Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseFactory.sucessRepsonse(Constants.ORDER_CANCELED);
    }

    @Override
    public GeneralResponse<?> paymentOrderFinish(OrderPaymentForm orderPaymentForm) {
        orderService.updateOrderPayment(orderPaymentForm);
        return ResponseFactory.sucessRepsonse(Constants.ORDER_PAYMENT_UPDATED);
    }

    @Override
    public GeneralResponse<?> getTotalServiceFee(List<OrderServiceDetailForm> detailFormList) {
        return ResponseFactory.sucessRepsonse(orderService.getServicesFee(detailFormList));
    }

    @Override
    public GeneralResponse<?> getShippingFee(Double distance) {
        return ResponseFactory.sucessRepsonse(orderService.getServicesFee(distance));
    }
}
