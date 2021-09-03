package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laundy.laundrybackend.constant.StaffUserRoleEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "staff_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"phone_number", "username"})})
public class StaffUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 6, max = 50)
    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Size(min = 6, max = 50)
    @Column(nullable = false)
    private String password;


    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffUserRoleEnum role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Order.class, mappedBy = "staffUser")
    @JsonBackReference
    private List<Order> orders;
}
