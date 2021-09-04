package com.laundy.laundrybackend.service.impl;

import com.laundy.laundrybackend.config.security.jwt.JwtUserProvider;
import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.models.Address;
import com.laundy.laundrybackend.models.User;
import com.laundy.laundrybackend.models.dtos.JwtResponseDTO;
import com.laundy.laundrybackend.models.request.RegisterUserForm;
import com.laundy.laundrybackend.models.request.UserLoginForm;
import com.laundy.laundrybackend.repository.AddressRepository;
import com.laundy.laundrybackend.repository.UserRepository;
import com.laundy.laundrybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUserProvider jwtProvider) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void registerNewUser(RegisterUserForm registerUserForm) {
        User user = User.getUserFromRegisterForm(registerUserForm);
        Address address = Address.addressFromRegisterUserForm(registerUserForm);
        address.setUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userRepository.existsByUsernameOrPhoneNumberOrEmail(user.getUsername(),user.getPhoneNumber(), user.getEmail())){
            addressRepository.save(address);
        }else {
            throw new ValidationException(Constants.USER_EXIST_ERROR_MESS);
        }
    }

    @Override
    public JwtResponseDTO loginUser(UserLoginForm userLoginForm) {
        User user = getUserByUsernameOrPhoneNumber(userLoginForm.getLoginId());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), userLoginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponseDTO(jwt,userDetails.getUsername());
    }

    private User getUserByUsernameOrPhoneNumber(String loginId){
        if (userRepository.findByUsername(loginId).isPresent()){
            return userRepository.findByUsername(loginId).get();
        }else if (userRepository.findUserByPhoneNumber(loginId).isPresent()){
            return userRepository.findUserByPhoneNumber(loginId).get();
        }
        throw new ValidationException(Constants.USER_NOT_EXISTED_ERROR);
    }
}
