package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.controller.api.UserInterface;
import com.laundy.laundrybackend.models.dtos.JwtResponseDTO;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import com.laundy.laundrybackend.models.request.UserLoginForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController implements UserInterface {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GeneralResponse<?> registerNewUser(RegisterUserForm registerUserForm) {
        userService.registerNewUser(registerUserForm);
        return ResponseFactory.sucessRepsonse(Constants.REGISTER_NEW_USER_SUCCESS);
    }

    @Override
    public GeneralResponse<?> login(UserLoginForm userLoginForm) {
        JwtResponseDTO responseDTO = userService.loginUser(userLoginForm);
        return ResponseFactory.sucessRepsonse(responseDTO);
    }
}
