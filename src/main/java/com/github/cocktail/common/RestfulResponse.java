package com.github.cocktail.common;

import com.github.cocktail.enums.CommonStatusEnum;
import com.github.cocktail.exception.IRestfulException;
import lombok.*;

import java.io.Serializable;

/**
 * @author Liuhao
 * Description:符合restful Api标准请求响应体
 * Date:15:21 2020/5/21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class RestfulResponse<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 请求成功
     */
    public static <T> RestfulResponse<T> success(T data) {
        return new RestfulResponse<>(true, data, CommonStatusEnum.OK.getCode(), CommonStatusEnum.OK.getMessage());
    }

    /**
     * 请求失败
     */
    public static <T> RestfulResponse<T> error(T data) {
        return new RestfulResponse<>(false, data, CommonStatusEnum.INTERNAL_SERVER_ERROR.getCode(), CommonStatusEnum.INTERNAL_SERVER_ERROR.getMessage());
    }

    /**
     * 请求失败
     */
    public static <T> RestfulResponse<T> error(IRestfulException iRestfulException) {
        return error(null, iRestfulException);
    }

    /**
     * 请求失败
     */
    public static <T> RestfulResponse<T> error(T data, IRestfulException iRestfulException) {
        return new RestfulResponse<>(false, data, iRestfulException.getCode(), iRestfulException.getMessage());
    }

}