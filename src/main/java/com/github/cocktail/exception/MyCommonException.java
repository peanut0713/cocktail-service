package com.github.cocktail.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Liuhao
 * Description:通用异常实体
 * Date:14:51 2020/5/21
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCommonException extends RuntimeException {

    /**
     * 符合restful标准的异常对象
     */
    private IRestfulException iRestfulException;

    public MyCommonException(IRestfulException iRestfulException) {
        this.iRestfulException = iRestfulException;
    }

    @Override
    public String getMessage() {
        return this.iRestfulException.getMessage();
    }

    public int getCode() {
        return this.iRestfulException.getCode();
    }

}