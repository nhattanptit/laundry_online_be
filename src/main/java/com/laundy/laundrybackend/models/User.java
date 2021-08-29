package com.laundy.laundrybackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints ={@UniqueConstraint(columnNames = {"phone_number","username","email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 100)
    private String name;

    @NotBlank
    @Size(min = 6, max = 50)
    private String username;

    @JsonIgnore
    @Size(min = 6)
    private String password;


    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;


    @NotBlank
    @Size(min = 6, max = 255)
    private String address;

    @Email
    @NotBlank
    private String email;

    public static User getUserFromRegisterForm(RegisterUserForm registerUserForm){
        return User.builder()
                .username(registerUserForm.getUsername())
                .password(registerUserForm.getPassword())
                .name(registerUserForm.getName())
                .address(registerUserForm.getAddress())
                .phoneNumber(registerUserForm.getPhoneNumber())
                .email(registerUserForm.getEmail())
                .build();
    }
}
