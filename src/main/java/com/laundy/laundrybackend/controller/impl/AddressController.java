package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.controller.api.AddressInterface;
import com.laundy.laundrybackend.models.request.AddressForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController implements AddressInterface {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public GeneralResponse<?> getAllAddressOfCurrentUser() {
        return ResponseFactory.sucessRepsonse(addressService.getAllAddressByCurrentUser());
    }

    @Override
    public GeneralResponse<?> getAddressById(Long addressId) {
        return ResponseFactory.sucessRepsonse(addressService.getAddressById(addressId));
    }

    @Override
    public GeneralResponse<?> createNewAddressForCurrentUser(AddressForm addressForm) {
        addressService.createNewAddressForCurrentUser(addressForm);
        return ResponseFactory.sucessRepsonse(Constants.NEW_ADDRESS_CREATED);
    }

    @Override
    public GeneralResponse<?> updateAddressInfo(Long addressId, AddressForm addressForm) {
        addressService.updateAddressInfo(addressId, addressForm);
        return ResponseFactory.sucessRepsonse(Constants.ADDRESS_INFO_UPDATED);
    }

    @Override
    public GeneralResponse<?> deleteAddressInfo(Long addressId) {
        return ResponseFactory.sucessRepsonse(addressService.deleteAddressById(addressId));
    }
}
