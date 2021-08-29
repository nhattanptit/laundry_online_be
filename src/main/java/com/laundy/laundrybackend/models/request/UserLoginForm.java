package com.laundy.laundrybackend.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginForm {
    @NotBlank
    @Size(min=3, max = 60)
    private String loginId;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
