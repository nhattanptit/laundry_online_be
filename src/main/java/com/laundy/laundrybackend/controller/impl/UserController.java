package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.config.security.jwt.JwtUserProvider;
import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.constant.ResponseStatusCodeEnum;
import com.laundy.laundrybackend.controller.api.UserInterface;
import com.laundy.laundrybackend.models.dtos.JwtResponseDTO;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import com.laundy.laundrybackend.models.request.UserLoginForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

//    @PostMapping("signup")
//    ResponseEntity<?> registerNewUser(@Valid @RequestBody RegisterUserForm registerUserForm){
//        User user = User.builder()
//                .name("test")
//                .username(registerUserForm.getUsername())
//                .password(passwordEncoder.encode(registerUserForm.getPassword()))
//                .address("Earth")
//                .phoneNumber(UUID.randomUUID().toString())
//                .build();
//        userRepository.save(user);
//        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
//    }
//
//    @PostMapping("signin")
//    public GeneralResponse<?> authenticateUser(@Valid @RequestBody RegisterUserForm registerUserForm) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(registerUserForm.getUsername(), registerUserForm.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtProvider.generateJwtToken(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return new GeneralResponse<Object>(ResponseStatusCodeEnum.SUCCESS.getCode(), new JwtResponseDTO(jwt,userDetails.getUsername()));
//    }
}
