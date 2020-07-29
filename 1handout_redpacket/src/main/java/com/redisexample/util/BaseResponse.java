package com.redisexample.util;

import lombok.Data;

/**
 * @author licha
 * @since 2020/7/29 10:59
 */
@Data
public class BaseResponse {
    String code="";
    String message="";
    Object data;

    public BaseResponse(String code) {
        this.code=code;
    }

    public BaseResponse(String code, String message) {
        this.code=code;
        this.message=message;
    }
    public BaseResponse(String code, String message,Object data) {
        this.code=code;
        this.message=message;
        this.data=data;
    }
}
