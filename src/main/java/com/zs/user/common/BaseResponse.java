package com.zs.user.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * 用于给前端返回对应的错误类型以区分各个不同报错以及问题；
 * @author zs
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable{
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description= description;
    }
    public BaseResponse(int code, T data, String message) {

        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {

        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}



