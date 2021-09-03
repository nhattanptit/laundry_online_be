package com.laundy.laundrybackend.controller.api;

import com.laundy.laundrybackend.constant.OrderStatusEnum;
import com.laundy.laundrybackend.models.request.NewOrderForm;
import com.laundy.laundrybackend.models.request.OrderPaymentForm;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.validator.ValueOfEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequestMapping("orders/")
@CrossOrigin("*")
public interface OrderInterface {
    @PostMapping("create")
    @Operation(description = "Tạo order mới", summary = "Tạo order mới")
    GeneralResponse<?> createNewOrder(@org.springframework.web.bind.annotation.RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Form Tạo order mới", required = true) NewOrderForm orderForm);

    @GetMapping("list")
    @Operation(description = "Get List order theo trạng thái order", summary = "Get List order theo trạng thái order")
    GeneralResponse<?> getOrderByStatus(@Parameter(name = "orderStatus", description = "Status của đơn hàng là 1 trong các giá trị sau NEW, SHIPPER_ACCEPTED_ORDER," +
            " SHIPPER_RECEIVED_ORDER, STORE_RECEIVED_ORDER, STORE_DONE_ORDER, SHIPPER_DELIVER_ORDER, COMPLETED_ORDER, CANCEL", example = "NEW")
                                        @ValueOfEnum(enumClass = OrderStatusEnum.class) @RequestParam(value = "orderStatus",required = false) String orderStatus, @RequestParam("page") @Parameter(description = "số trang", example = "0",required = true) @NotNull int page, @RequestParam("size") @Parameter(description = "sô bản ghi của trang", example = "2",required = true) @NotNull int size);


    @PostMapping("detail")
    @Operation(description = "Chi tiết order", summary = "Chi tiết order")
    GeneralResponse<?> getOrderDetail(@Parameter(name = "orderId", description = "Id đơn hàng",required = true) @RequestParam("orderId") @NotNull Long orderId);

    @PostMapping("cancel")
    @Operation(description = "Hủy order", summary = "Hủy order")
    GeneralResponse<?> cancelOrder(@Parameter(name = "orderId", description = "Id đơn hàng",required = true) @RequestParam("orderId") @NotNull Long orderId);

    @PostMapping("payment-done")
    @Operation(description = "Hoàn thành thanh toán order", summary = "Hoàn thành thanh toán order")
    GeneralResponse<?> paymentOrderFinish(@org.springframework.web.bind.annotation.RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Form thông tin thanh toán", required = true) OrderPaymentForm orderPaymentForm);
}
