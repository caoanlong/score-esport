package com.dragon.scoreapi.model.exception;

import com.dragon.scoreapi.enums.ResCode;

public class CommonException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CommonException(ResCode resCode) {
        super(resCode.getMessage());
        this.code = resCode.getCode();
    }
}
