package com.lld.autocode.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName: ResponseUtil
 * @Description: ResponseUtil
 * @Author wzl
 * @Date 2020/12/29 20:14
 * @Version 1.0
 */
public class ResponseUtil {
    public static void out(HttpServletResponse response, ReturnMessage returnMessage) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, returnMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}

