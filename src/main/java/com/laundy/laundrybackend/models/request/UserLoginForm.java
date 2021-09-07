package com.laundy.laundrybackend.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginForm {
    @NotBlank
    @NotNull
    private String loginId;

    @NotBlank
    @NotNull
    private String password;

}
