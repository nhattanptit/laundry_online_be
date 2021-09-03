package com.laundy.laundrybackend.constant;

import java.math.BigDecimal;

public class Constants {
    public static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    public static final String REGISTER_NEW_USER_SUCCESS = "Đăng kí thành công";
    public static final String USER_EXIST_ERROR_MESS = "User infos existed";
    public static final String USER_NOT_EXISTED_ERROR = "User not existed";
    public static final String ORDER_CANCELED = "Hủy đơn hàng thành công";
    public static final String ORDER_PAYMENT_UPDATED = "Thông tin thanh toán đơn hàng cập nhật thành công";
    public static final BigDecimal VAT_VALUES = BigDecimal.valueOf(1.1);
    private Constants(){

    }
}
