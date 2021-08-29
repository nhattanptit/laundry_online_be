package com.laundy.laundrybackend.controller.api;

import com.laundy.laundrybackend.models.response.GeneralResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("services/")
@Validated
public interface ServiceInterface {
    @GetMapping("getAll")
    @Operation(description = "Load danh sách các dịch vụ hiện có", summary = "Load danh sách các dịch vụ hiện có")
    GeneralResponse<?> getAllService();
}
