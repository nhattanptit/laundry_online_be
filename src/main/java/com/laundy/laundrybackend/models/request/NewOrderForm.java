package com.laundy.laundrybackend.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderForm {
    @NotNull
    private Double distance;
    @NotNull
    private Long serviceId;
    @NotNull
    private BigDecimal totalShipFee;
    @NotNull
    private BigDecimal totalServiceFee;
    @NotNull
    private List<OrderServiceDetailForm> orderServiceDetailForms;

    @NotBlank
    @NotNull
    private String shippingAddress;

    @NotBlank
    @NotNull
    private String shippingPersonName;

    @NotBlank
    @NotNull
    private String shippingPersonPhoneNumber;

    @NotBlank
    @NotNull
    private String pickUpAddress;

    @NotBlank
    @NotNull
    private String pickUpPersonName;

    @NotBlank
    @NotNull
    private String pickUpPersonPhoneNumber;
}
