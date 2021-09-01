package com.laundy.laundrybackend.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderForm {
    private Double distance;
    private Long serviceId;
    private List<OrderServiceDetailForm> orderServiceDetailForms;
    private String shippingAddress;
}
