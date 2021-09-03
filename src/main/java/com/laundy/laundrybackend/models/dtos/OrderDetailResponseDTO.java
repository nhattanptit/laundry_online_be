package com.laundy.laundrybackend.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponseDTO {
    private Long id;

    private Long serviceId;
    private String serviceName;

    private OrderStatusEnum status;

    private Double distance;

    private BigDecimal totalShipFee;

    private BigDecimal totalServiceFee;

    private BigDecimal totalBill;

    private String deliverAddress;

    private String pickUpAddress;

    @JsonProperty
    private Boolean isPaid;

    @JsonProperty
    private PaymentInfo paymentInfo;

    private String createdDate;

    private String lastUpdatedDate;

    private String pickUpDateTime;

    private String deliveryDateTime;

    @JsonProperty("shipper")
    private StaffUser staffUser;

    @JsonProperty("serviceDetails")
    private List<OrderServiceDetailDTO> orderServiceDetailDTOS;

    public static OrderDetailResponseDTO OrderDetailResponseDTOFromOrderAndService(Order order, Service service, List<OrderServiceDetail> serviceDetails) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY hh:ss");
        return OrderDetailResponseDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalShipFee(order.getTotalShipFee())
                .totalServiceFee(order.getTotalServiceFee())
                .totalBill(order.getTotalBill())
                .deliverAddress(order.getShippingAddress())
                .pickUpAddress(order.getShippingAddress())
                .staffUser(order.getStaffUser() == null ? null : order.getStaffUser())
                .createdDate(formatter.format(order.getCreatedDate()))
                .lastUpdatedDate((formatter.format(order.getLastUpdatedDate())))
                .pickUpDateTime(order.getPickUpDateTime() == null ? null : formatter.format(order.getPickUpDateTime()))
                .deliveryDateTime(order.getDeliveryDateTime() == null ? null : formatter.format(order.getDeliveryDateTime()))
                .serviceName(service.getName())
                .serviceId(service.getId())
                .distance(order.getDistance())
                .isPaid(order.getIsPaid())
                .paymentInfo(order.getPaymentInfo())
                .orderServiceDetailDTOS(OrderServiceDetailDTO.orderServiceDetailDTOSFromOrderServiceDetails(serviceDetails))
                .build();
    }
}
