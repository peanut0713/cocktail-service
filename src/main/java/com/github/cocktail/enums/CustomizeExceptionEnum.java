package com.github.cocktail.enums;

import com.github.cocktail.exception.IRestfulException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Liuhao
 * Description:自定义异常枚举类
 * Date:15:49 2020/5/21
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum CustomizeExceptionEnum implements IRestfulException {

    //自定义异常信息
    INVALID_KEY(10001, "无效的Key"),
    INVALID_VALUE(10002, "无效的Value"),

    ;

    private int code;

    private String message;

}
