package com.github.cocktail.core;

import com.github.cocktail.common.RestfulResponse;
import com.github.cocktail.enums.CommonStatusEnum;
import com.github.cocktail.exception.MyCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author Liuhao
 * Description:全局异常处理，统一抓取返回标准RestfulResponse
 * Date:16:08 2020/5/21
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class MyRestfulExceptionHandler {

    /**
     * 全局系统异常
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public RestfulResponse<Void> exceptionHandler(HttpServletRequest req, Exception e) {
        //todo 异步发送错误日志
        log.info("INTERNAL_SERVER_ERROR; message:{},cause:{}", e.getMessage(), e.getCause());
        return RestfulResponse.error(CommonStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 自定义系统异常
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(value = MyCommonException.class)
    public RestfulResponse<Void> myCommonExceptionHandler(HttpServletRequest req, MyCommonException e) {
        //todo 异步发送错误日志
        log.info("COMMON EXCEPTION; message:{},code:{}", e.getMessage(), e.getCode());
        return RestfulResponse.error(e.getIRestfulException());
    }

    /**
     * 所有javax的校验机制validation失败丢出来的异常
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public RestfulResponse<Void> validationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        //todo 异步发送错误日志
        //Set里面包含所有ConstraintViolation对象，直接抛出逗号分隔的所有异常message
        String allErrorMessages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        log.info("Constraint Violation Exception; errorMessage:{}", allErrorMessages);
        return new RestfulResponse<>(false, null, CommonStatusEnum.BAD_REQUEST.getCode(), allErrorMessages);
    }

}