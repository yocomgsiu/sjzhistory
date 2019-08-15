package com.sjzHistory.entity;

/**
 * 统一封装API返回信息
 * 千万别加@Entity 否则Hibernate会给你创建表
 * Created by Yocomg on 2019/7/16
 */
public class RespResult {
    //状态码
    private int code;
    //消息
    private String message;
    //额外的内容
    private Object data;

    public RespResult(){

    }

    public RespResult setCode(ResultCode code) {
        this.code = code.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public RespResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RespResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RespResult setData(Object data) {
        this.data = data;
        return this;
    }
}
