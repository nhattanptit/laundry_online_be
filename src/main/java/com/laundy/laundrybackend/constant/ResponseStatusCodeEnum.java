package com.laundy.laundrybackend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatusCodeEnum {
    SUCCESS("200",200),
    UNAUTHORIZED("401",401),
    REGISTER_EXISTED_USER_ERROR("L1",400),
    VALIDATE_DATA_ERROR("L2",400),
    USER_NOT_EXISTED_ERROR("L3",400),
    ORDER_CANNOT_BE_CANCEL("L4",400),
    INTERNAL_SERVER_ERROR("500",500);

    private final String code;
    private final int httpCode;
}
