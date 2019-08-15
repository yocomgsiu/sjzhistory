package com.sjzHistory.utils;


import org.springframework.stereotype.Component;

import com.sjzHistory.entity.RespResult;
import com.sjzHistory.entity.ResultCode;

/**
 * 工厂模式
 * 接口信息生成工具
 * 。@Component 添加到Spring组件中
 * Created by Yocomg on 2019/7/16
 */
@Component
public class ResultGenerator {

    private static final String SUCCESS = "success";
    //成功
    public RespResult getSuccessResult() {
        return new RespResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS);
    }
    //成功，附带额外数据
    public  RespResult getSuccessResult(Object data) {
        return new RespResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }
    //成功，自定义消息及数据
    public  RespResult getSuccessResult(String message,Object data) {
        return new RespResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }
    //失败，附带消息
    public  RespResult getFailResult(String message) {
        return new RespResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    //失败，自定义消息及数据
    public RespResult getFailResult(String message, Object data) {
        return new RespResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message)
                .setData(data);
    }
    //自定义创建
    public RespResult getFreeResult(ResultCode code, String message, Object data) {
        return new RespResult()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }
}
