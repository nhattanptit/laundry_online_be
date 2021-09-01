package com.laundy.laundrybackend.models.dtos;

import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.Order;
import com.laundy.laundrybackend.models.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;

    private Long serviceId;
    private String serviceName;

    private OrderStatusEnum status;

    private BigDecimal totalShipFee;

    private BigDecimal totalServiceFee;

    private BigDecimal totalBill;

    private String deliverAddress;

    private String pickUpAddress;

    private Long userId;

    private Long staffUserId;

    private String createdDate;

    private String lastUpdatedDate;

    private String pickUpDateTime;

    private String deliveryDateTime;

    public static OrderResponseDTO orderResponseDTOFromOrderAndService(Order order, Service service){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY hh:ss");
        return OrderResponseDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalShipFee(order.getTotalShipFee())
                .totalServiceFee(order.getTotalServiceFee())
                .totalBill(order.getTotalBill())
                .deliverAddress(order.getShippingAddress())
                .pickUpAddress(order.getShippingAddress())
                .userId(order.getUser().getId())
                .staffUserId(order.getStaffUser() == null? null : order.getStaffUser().getId())
                .createdDate(formatter.format(order.getCreatedDate()))
                .lastUpdatedDate((formatter.format(order.getLastUpdatedDate())))
                .pickUpDateTime(order.getPickUpDateTime() == null? null: formatter.format(order.getPickUpDateTime()))
                .deliveryDateTime(order.getDeliveryDateTime() == null? null: formatter.format(order.getDeliveryDateTime()))
                .serviceName(service.getName())
                .serviceId(service.getId())
                .build();
    }
}
