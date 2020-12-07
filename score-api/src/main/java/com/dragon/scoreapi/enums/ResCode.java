package com.dragon.scoreapi.enums;

public enum ResCode {

    /**
     * 用户名不能为空
     */
    SUCCESS(200, "成功"),
    USERNAME_NOTFOUNT(100, "用户不存在"),
    PHONE_NOTNULL(101, "手机号不能为空"),
    TOKEN_ERROR(1001, "token错误"),
    CODE_ERROR(1002, "验证码错误");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
