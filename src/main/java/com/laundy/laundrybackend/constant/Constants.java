package com.laundy.laundrybackend.constant;

import java.math.BigDecimal;

public class Constants {
    public static final String PHONE_NUMBER_REGEX = "(\\+[0-9]+[\\- \\.]*)?(\\([0-9]+\\)[\\- \\.]*)?([0-9][0-9\\- \\.]+[0-9])";

    public static final String REGISTER_NEW_USER_SUCCESS = "Register new user successfully";
    public static final String USER_EXIST_ERROR_MESS = "User infos existed";
    public static final String USER_NOT_EXISTED_ERROR = "User not existed";
    public static final String ORDER_CANCELED = "Order canceled";
    public static final String ORDER_PAYMENT_UPDATED = "Order payment info updated";
    public static final String NEW_ADDRESS_CREATED = "New Address created for User";
    public static final String ADDRESS_INFO_UPDATED = "Address info updated";
    public static final BigDecimal VAT_VALUES = BigDecimal.valueOf(1.1);
    private Constants(){

    }
}
