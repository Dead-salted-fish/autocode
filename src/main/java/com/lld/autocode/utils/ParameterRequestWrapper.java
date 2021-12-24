package com.lld.autocode.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/24 10:49
 */

public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<String, String[]>();
    HttpServletRequest copyRequest;

    @SuppressWarnings("unchecked")
    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.copyRequest = request;
        initParams(request);
    }

    //重载一个构造方法
    public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        addAllParameters(extendParams);//这里将扩展参数写入参数表
    }

    private void initParams(HttpServletRequest request) {

        String contentType = request.getHeader("Content-Type");

        if (contentType.contains("application/json")) {
           parseWithJson(request);
        } else if (contentType.contains("application/x-www-form-urlencoded")) {
            parseWithForm(request);
        }

    }

    @Override
    public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    public String[] getParameterValues(String name) {//同上
        return params.get(name);
    }

    public void addParameter(String name, Object value) {//增加参数
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    public void addAllParameters(Map<String, Object> otherParams) {//增加多个参数
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    private void parseWithForm(HttpServletRequest req) {

        Map<String, String[]> parameterMap = req.getParameterMap();
        params.putAll(parameterMap);
    }

    private void parseWithJson(HttpServletRequest req) {
        try {
            Map<String, Object> originalMap = new ObjectMapper().readValue(req.getInputStream(), Map.class);
            for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

