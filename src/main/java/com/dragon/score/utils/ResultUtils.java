package com.dragon.score.utils;

import com.dragon.score.enums.ResCode;
import com.dragon.score.model.ResultBean;

public class ResultUtils {

    public static ResultBean<Object> success(Object object) {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(ResCode.SUCCESS.getCode());
        result.setMessage(ResCode.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static ResultBean<Object> success() {
        return success(null);
    }

    public static ResultBean<Object> error(Integer code, String message) {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static ResultBean<Object> error(ResCode resCode) {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(resCode.getCode());
        result.setMessage(resCode.getMessage());
        return result;
    }
}
