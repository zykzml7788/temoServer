package com.creams.temo.enums;

public enum SysCode {
    // 成功
    SUCCESS_CODE(200),
    // 参数错误
    PARAMS_ERR_CODE(400),
    // 系统错误
    SERVICE_ERR_CODE(500),
    ;

    private Integer code;

    SysCode(int code) {
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }


    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
