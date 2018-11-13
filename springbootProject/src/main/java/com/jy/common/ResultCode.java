package com.jy.common;

/**
 * shenwei
 */
public enum ResultCode {
    SUCCESS(0, "成功"),
    FAILURE(-1, "失败"),
    ERROR(500, "服务器异常");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
