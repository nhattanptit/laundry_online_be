package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "service_prices")
public class ServicePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "service_id")
    private Service service;

    @Size(max = 7)
    @Column(length = 1)
    private Integer minLoad;

    @Size(max = 10)
    @Column(length = 2)
    private Integer maxLoad;

    @NotNull
    private BigDecimal price;
}
