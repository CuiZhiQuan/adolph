package com.transaction.adolph.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author cuizhiquan
 * @Description
 * @date 2018/5/14 10:25
 * @Copyright (c) 2017, YiQuanTech All Rights Reserved.
 */
public class JSONMessage extends JSONObject {

    private static final long serialVersionUID = 1L;

    /**
     * 失败
     */
    public static final Integer FAILURE = 0;

    /**
     * 成功
     */
    public static final Integer SUCCESS = 1;

    public JSONMessage() {}

    public JSONMessage(int resultCode, String resultMsg) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
    }

    public JSONMessage(int resultCode, String resultMsg, String detailMsg) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
        setDetailMsg(detailMsg);
    }

    public JSONMessage(int resultCode, String resultMsg, Object data) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
        setData(data);
    }

    public JSONMessage(String groupCode, String serviceCode, String nodeCode,
                       String resultMsg) {
        setResultCode(new StringBuffer().append(groupCode).append(serviceCode)
                .append(nodeCode).toString());
        setResultMsg(resultMsg);
    }

    public static JSONMessage success() {
        return success(null, null);
    }

    public static JSONMessage success(String resultMsg) {
        return new JSONMessage(SUCCESS, resultMsg);
    }

    public static JSONMessage success(Object data) {
        return success(null, data);
    }

    public static JSONMessage success(String resultMsg, Object data) {
        return new JSONMessage(SUCCESS, resultMsg, data);
    }

    public static JSONMessage failure(String resultMsg) {
        return new JSONMessage(FAILURE, resultMsg);
    }

    public static JSONMessage error(Exception e) {
        return new JSONMessage(10001, "服务器繁忙，请稍后再试！", e.getMessage());
    }

    public Object getResultCode() {
        return get("resultCode");
    }

    public void setResultCode(Object resultCode) {
        put("resultCode", resultCode);
    }

    public String getResultMsg() {
        return getString("resultMsg");
    }

    public void setResultMsg(String resultMsg) {
        put("resultMsg", resultMsg);
    }

    public String getDetailMsg() {
        return getString("detailMsg");
    }

    public void setDetailMsg(String detailMsg) {
        put("detailMsg", detailMsg);
    }

    public Object getData() {
        return get("data");
    }

    public void setData(Object data) {
        put("data", data);
    }
}
