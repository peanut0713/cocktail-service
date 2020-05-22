package com.github.cocktail.exception;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author Liuhao
 * Description:符合restful标准的异常接口
 * Date:14:48 2020/5/21
 */
public interface IRestfulException extends Serializable {

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 构建异常对象
     *
     * @return 自定义异常对象
     */
    default Supplier<MyCommonException> buildException() {
        return () -> new MyCommonException(this);
    }
}
