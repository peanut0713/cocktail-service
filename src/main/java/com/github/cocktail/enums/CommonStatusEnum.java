package com.github.cocktail.enums;

import com.github.cocktail.exception.IRestfulException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Liuhao
 * Description:通用HTTP请求状态码枚举类
 * Date:16:04 2020/5/21
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IRestfulException {

    //1XX错误（请求收到，继续处理）
    CONTINUE(100, "继续。客户端应继续其请求"),
    SWITCHING_PROTOCOLS(101, "切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议"),

    //2XX成功（操作成功收到，分析、接受）
    OK(200, "请求成功"),
    CREATED(201, "已创建。成功请求并创建了新的资源"),
    ACCEPTED(202, "已接受。已经接受请求，但未处理完成"),
    NON_AUTHORITATIVE_INFORMATION(203, "非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本"),

    //3XX错误（完成此请求必须进一步处理）
    MULTIPLE_CHOICES(300, "多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端"),
    MOVED_PERMANENTLY(301, "已创建。成功请求并创建了新的资源"),
    FOUND(302, "永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。"),
    SEE_OTHER(303, "临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI。"),

    //4XX错误（请求包含一个错误语法或不能完成）
    BAD_REQUEST(400, "客户端发出的请求不支持或无法识别"),
    UNAUTHORIZED(401, "访问资源或者执行请求的状态转换之前需要进行认证或授权"),
    FORBIDDEN(403, "没有访问资源或者执行请求状态转换的权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持请求的状态转换(http方法)"),
    NOT_ACCEPTABLE(406, "服务器不支持Accept头中请求的表示格式(http方法)"),

    //5XX错误（服务器执行一个完全有效请求失败）
    INTERNAL_SERVER_ERROR(500, "系统异常"),
    NOT_IMPLEMENTED(501, "服务器不支持请求的功能，无法完成请求"),
    BAD_GATEWAY(502, "作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应"),

    ;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String message;

}
