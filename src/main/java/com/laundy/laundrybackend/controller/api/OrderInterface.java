package com.laundy.laundrybackend.controller.api;

import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("orders/")
@CrossOrigin("*")
public interface OrderInterface {
    @PostMapping("create")
    @Operation(description = "Tạo order mới", summary = "Tạo order mới")
    GeneralResponse<?> createNewOrder(@org.springframework.web.bind.annotation.RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Form Tạo order mới", required = true)NewOrderForm orderForm);
}
