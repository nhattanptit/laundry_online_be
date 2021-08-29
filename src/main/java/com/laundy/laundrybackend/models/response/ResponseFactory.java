package com.laundy.laundrybackend.models.response;

import com.laundy.laundrybackend.constant.ResponseStatusCodeEnum;

public class ResponseFactory {
    public static final GeneralResponse<Object> sucessRepsonse(Object data){
        return new GeneralResponse<>(ResponseStatusCodeEnum.SUCCESS.getCode(), data);
    }
}
