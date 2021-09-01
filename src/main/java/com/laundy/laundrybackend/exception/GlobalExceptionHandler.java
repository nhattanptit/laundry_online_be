package com.laundy.laundrybackend.exception;

import com.laundy.laundrybackend.constant.Constants;
import com.laundy.laundrybackend.constant.ResponseStatusCodeEnum;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ValidationException;

//@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAllException(Exception e) {
        return createResponse(ResponseStatusCodeEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public final ResponseEntity<Object> handleUnauthorizedException(Exception e) {
        return createResponse(ResponseStatusCodeEnum.UNAUTHORIZED);
    }

    @ExceptionHandler({ValidationException.class})
    public final ResponseEntity<Object> handleValidationException(Exception e) {
        if (e.getMessage().equals(Constants.USER_EXIST_ERROR_MESS)) {
            return createResponse(ResponseStatusCodeEnum.REGISTER_EXISTED_USER_ERROR
            );
        }
        return createResponse(ResponseStatusCodeEnum.VALIDATE_DATA_ERROR);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(Exception e) {
        return createResponse(ResponseStatusCodeEnum.VALIDATE_DATA_ERROR);
    }

    private ResponseEntity<Object> createResponse(ResponseStatusCodeEnum statusCodeEnum) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(statusCodeEnum.getCode());
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(statusCodeEnum.getHttpCode()));
    }
}
