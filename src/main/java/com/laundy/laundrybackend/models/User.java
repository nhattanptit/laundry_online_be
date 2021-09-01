package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"phone_number", "username", "email"})})
public class User{
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
    @Size(min = 6)
    @Column(nullable = false)
    private String password;


    @NotBlank
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String city;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String district;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String ward;

    @NotBlank
    @Size(min = 6, max = 255)
    private String address;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Order.class, mappedBy = "user")
    @JsonBackReference
    private List<Order> orders;

    public static User getUserFromRegisterForm(RegisterUserForm registerUserForm) {
        return User.builder()
                .username(registerUserForm.getUsername())
                .password(registerUserForm.getPassword())
                .name(registerUserForm.getName())
                .city(registerUserForm.getCity())
                .district(registerUserForm.getDistrict())
                .ward(registerUserForm.getWard())
                .address(registerUserForm.getAddress())
                .phoneNumber(registerUserForm.getPhoneNumber())
                .email(registerUserForm.getEmail())
                .build();
    }
}
