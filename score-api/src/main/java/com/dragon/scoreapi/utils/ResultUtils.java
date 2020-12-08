package com.dragon.scoreapi.utils;

import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.ResultBean;

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

    public static String ok() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("message", "成功");
        return jsonObject.toJSONString();
    }

    public static String err(ResCode resCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", resCode.getCode());
        jsonObject.put("message", resCode.getMessage());
        return jsonObject.toJSONString();
    }

    public static String err(Integer code, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }
}
