package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

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
    @JoinColumn(name = "service_id",nullable = false )
    private Service service;

    @Column(nullable = false)
    private String name;

    @NotNull
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderServiceDetail.class, mappedBy = "serviceDetail")
    @JsonBackReference
    private Set<OrderServiceDetail> orderServiceDetails;
}
