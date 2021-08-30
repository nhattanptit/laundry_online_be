package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_service_details")
public class OrderServiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "service_detail_id", nullable = false)
    private ServiceDetail serviceDetail;
}
