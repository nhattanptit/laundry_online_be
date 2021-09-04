package com.laundy.laundrybackend.controller.api;

import com.laundy.laundrybackend.models.request.AddressForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping("addresses/")
@CrossOrigin("*")
public interface AddressInterface {
    @GetMapping("all")
    GeneralResponse<?> getAllAddressOfCurrentUser();

    @GetMapping("")
    GeneralResponse<?> getAddressById(@RequestParam("addressId") @Parameter(name = "addressId",description = "Id của address",required = true) @NotNull Long addressId);

    @PostMapping("new")
    GeneralResponse<?> createNewAddressForCurrentUser(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Form thông tin address", required = true) AddressForm addressForm);

    @PutMapping("update")
    GeneralResponse<?> updateAddressInfo(@RequestParam("addressId") @Parameter(name = "addressId",description = "Id của address",required = true) @NotNull Long addressId, @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Form thông tin address", required = true) AddressForm addressForm);

    @DeleteMapping("delete")
    GeneralResponse<?> deleteAddressInfo(@RequestParam("addressId") @Parameter(name = "addressId",description = "Id của address",required = true) @NotNull Long addressId);

}
