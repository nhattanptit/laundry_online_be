package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laundy.laundrybackend.constant.ServiceDetailIconEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "service_details")
public class ServiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServiceDetailIconEnum serviceDetailIcon;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderServiceDetail.class, mappedBy = "serviceDetail")
    @JsonBackReference
    private List<OrderServiceDetail> orderServiceDetails;
}
