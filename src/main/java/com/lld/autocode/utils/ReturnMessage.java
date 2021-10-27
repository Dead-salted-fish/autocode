package com.lld.autocode.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/27 11:28
 */
public class ReturnMessage implements Serializable {
    private String code;
    private String message;
    private Object returnData;
    private Map<String, Object> paramInfo = new LinkedHashMap();


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    public Map<String, Object> getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(Map<String, Object> paramInfo) {
        this.paramInfo = paramInfo;
    }

    public  static  ReturnMessage ok(Object object){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setCode("200");
        returnMessage.setReturnData(object);
        return  returnMessage;
    }

    public  static  ReturnMessage error(Object object){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setCode("400");
        returnMessage.setReturnData(object);
        return  returnMessage;
    }
}
