package com.laundy.laundrybackend.service;

import com.laundy.laundrybackend.models.dtos.JwtResponseDTO;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import com.laundy.laundrybackend.models.request.UserLoginForm;

public interface UserService {
    void registerNewUser(RegisterUserForm registerUserForm);
    JwtResponseDTO loginUser(UserLoginForm userLoginForm);
}
