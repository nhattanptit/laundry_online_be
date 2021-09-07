package com.laundy.laundrybackend.service.impl;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.exception.OrderCannotBeCancelException;
import com.laundy.laundrybackend.exception.UnauthorizedException;
import com.laundy.laundrybackend.models.*;
import com.laundy.laundrybackend.models.dtos.OrderDetailResponseDTO;
import com.laundy.laundrybackend.models.dtos.OrderResponseDTO;
import com.laundy.laundrybackend.models.dtos.OrderServiceDetailDTO;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderPaymentForm;
import com.laundy.laundrybackend.models.request.OrderServiceDetailForm;
import com.laundy.laundrybackend.models.response.ServicesFeeResponse;
import com.laundy.laundrybackend.repository.*;
import com.laundy.laundrybackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final OrderServiceDetailRepository orderServiceDetailRepository;
    private final OrderRepository orderRepository;
    private final ShipFeeRepository shipFeeRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderServiceImpl(ServiceDetailsRepository serviceDetailsRepository, OrderServiceDetailRepository orderServiceDetailRepository, OrderRepository orderRepository, ShipFeeRepository shipFeeRepository, UserRepository userRepository, ServiceRepository serviceRepository, AddressRepository addressRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.orderServiceDetailRepository = orderServiceDetailRepository;
        this.orderRepository = orderRepository;
        this.shipFeeRepository = shipFeeRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public OrderDetailResponseDTO createNewOrder(NewOrderForm orderForm) {
        ShipFee shipFee = shipFeeRepository.getShipFeeByDistance(orderForm.getDistance());
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Address userDefaultAddress = addressRepository.getUserDefaultAddress(currentUser.getId());
        Order order = Order.builder()
                .distance(orderForm.getDistance())
                .status(OrderStatusEnum.NEW)
                .shipFee(shipFee)
                .shippingAddress(orderForm.getShippingAddress())
                .totalShipFee(orderForm.getTotalShipFee())
                .totalServiceFee(orderForm.getTotalServiceFee())
                .totalBill((orderForm.getTotalServiceFee().add(orderForm.getTotalShipFee())).multiply(Constants.VAT_VALUES))
                .user(currentUser)
                .isPaid(Boolean.FALSE)
                .pickUpPersonName(userDefaultAddress.getReceiverName())
                .pickUpAddress(String.join(", ",new String[]{userDefaultAddress.getAddress(), userDefaultAddress.getWard(), userDefaultAddress.getDistrict(),userDefaultAddress.getCity()}))
                .pickUpPersonPhoneNumber(userDefaultAddress.getReceiverPhoneNumber())
                .shippingPersonPhoneNumber(orderForm.getShippingPersonPhoneNumber())
                .shippingPersonName(orderForm.getShippingPersonName())
                .build();
        com.laundy.laundrybackend.models.Service service = serviceRepository.getById(orderForm.getServiceId());
        List<OrderServiceDetail> serviceDetails = orderServiceDetailsFromNewOrderForm(orderForm,order);
        orderServiceDetailRepository.saveAllAndFlush(serviceDetails);
        return OrderDetailResponseDTO.OrderDetailResponseDTOFromOrderAndService(order,service,serviceDetails);
    }

    @Override
    public List<OrderResponseDTO> getOrderByStatus(OrderStatusEnum status, int page, int size) {
        Pageable pageReq
                = PageRequest.of(page,size);
        List<Order> orders = orderRepository.getUserOrderByStatusAndUsername(status, SecurityContextHolder.getContext().getAuthentication().getName(),pageReq);
        List<OrderResponseDTO> responseDTOS = new ArrayList<>();
        for (Order order: orders){
            com.laundy.laundrybackend.models.Service service = serviceRepository.getServiceByOrderId(order.getId());
            responseDTOS.add(OrderResponseDTO.orderResponseDTOFromOrderAndService(order,service));
        }
        return responseDTOS;
    }

    @Override
    public OrderDetailResponseDTO getOrderDetail(Long orderId) {
        Order order = orderRepository.getById(orderId);
        com.laundy.laundrybackend.models.Service service = serviceRepository.getServiceByOrderId(orderId);
        List<OrderServiceDetail> serviceDetails = orderServiceDetailRepository.findAllByOrderId(orderId);
        return OrderDetailResponseDTO.OrderDetailResponseDTOFromOrderAndService(order,service,serviceDetails);
    }

    @Override
    @Transactional
    public NewOrderForm cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            if (!order.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                throw new UnauthorizedException("USER NOT OWN THE ORDER");
            }
            if (!checkIfOrderIsCancelable(order)){
                throw new OrderCannotBeCancelException("ORDER CAN'T BE CANCEL");
            }else{
                order.setIsPaid(false);
                order.setStatus(OrderStatusEnum.CANCEL);
                orderRepository.save(order);
                return getReOrderInfo(order);
            }
        }else{
            throw new NoResultException("NO ORDER MATCH ID");
        }
    }

    @Override
    @Transactional
    public void updateOrderPayment(OrderPaymentForm orderPaymentForm) {
        PaymentInfo paymentInfo = PaymentInfo.paymentInfoFromOrderPaymentForm(orderPaymentForm);
        try{
            Order order = orderRepository.getById(orderPaymentForm.getOrderId());
            order.setIsPaid(Boolean.TRUE);
            order.setPaymentInfo(paymentInfo);
            orderRepository.save(order);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public BigDecimal getServicesFee(Double distance) {
        ShipFee shipFee = shipFeeRepository.getShipFeeByDistance(distance);
        if (shipFee == null) throw new NoResultException("No shipfee found match distance");
        return shipFee.getFee();
    }

    @Override
    public ServicesFeeResponse getServicesFee(List<OrderServiceDetailForm> detailFormList) {
        BigDecimal totalServicesFee = BigDecimal.ZERO;
        List<OrderServiceDetailDTO> serviceDetailDTOS = new ArrayList<>();
        List<Long> ids = detailFormList.stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        List<ServiceDetail> serviceDetails = serviceDetailsRepository.findAllById(ids);
        if (serviceDetails.isEmpty()) throw new NoResultException("NO SERVICE DETAIL");
        for (OrderServiceDetailForm serviceDetailForm: detailFormList){
            ServiceDetail serviceDetail = serviceDetails.stream()
                    .filter(detail -> serviceDetailForm.getServiceDetailId().equals(detail.getId()))
                    .findAny()
                    .orElse(null);
            totalServicesFee = totalServicesFee.add(BigDecimal.valueOf(serviceDetailForm.getQuantity()).multiply((serviceDetail.getPrice())));
            serviceDetailDTOS.add(OrderServiceDetailDTO.orderServiceDetailDTOFromServiceDetail(serviceDetail,serviceDetailForm.getQuantity()));
        }
        return new ServicesFeeResponse(totalServicesFee,serviceDetailDTOS);
    }

    private List<OrderServiceDetail> orderServiceDetailsFromNewOrderForm(NewOrderForm orderForm, Order order){
        List<Long> ids = orderForm.getOrderServiceDetails().stream().map(OrderServiceDetailForm::getServiceDetailId).collect(Collectors.toList());
        List<ServiceDetail> serviceDetails = serviceDetailsRepository.findAllById(ids);
        if (serviceDetails.isEmpty()) throw new NoResultException("NO SERVICE DETAIL");
        List<OrderServiceDetail> orderServiceDetails = new ArrayList<>();
        for (OrderServiceDetailForm serviceDetailForm: orderForm.getOrderServiceDetails()){
            ServiceDetail serviceDetail = serviceDetails.stream()
                    .filter(detail -> serviceDetailForm.getServiceDetailId().equals(detail.getId()))
                    .findAny()
                    .orElse(null);

            orderServiceDetails.add(OrderServiceDetail.builder()
                    .order(order)
                    .serviceDetail(serviceDetail)
                    .quantity(serviceDetailForm.getQuantity())
                    .build());
        }
        return orderServiceDetails;
    }

    private NewOrderForm getReOrderInfo(Order order){
        com.laundy.laundrybackend.models.Service service = serviceRepository.getServiceByOrderId(order.getId());
        List<OrderServiceDetailForm> detailForms = new ArrayList<>();
        for (OrderServiceDetail orderServiceDetail: order.getOrderServiceDetails()){
            detailForms.add(new OrderServiceDetailForm(orderServiceDetail.getServiceDetail().getId(),orderServiceDetail.getQuantity()));
        }
        return NewOrderForm.builder()
                .serviceId(service.getId())
                .distance(order.getDistance())
                .orderServiceDetails(detailForms)
                .totalServiceFee(order.getTotalServiceFee())
                .shippingAddress(order.getShippingAddress())
                .shippingPersonName(order.getShippingPersonName())
                .shippingPersonPhoneNumber(order.getShippingPersonPhoneNumber())
                .totalShipFee(order.getTotalShipFee())
                .build();
    }

    private boolean checkIfOrderIsCancelable(Order order){
        if (order.getStatus().equals(OrderStatusEnum.NEW) || order.getStatus().equals(OrderStatusEnum.SHIPPER_ACCEPTED_ORDER)){
            return true;
        }
        return false;
    }
}
