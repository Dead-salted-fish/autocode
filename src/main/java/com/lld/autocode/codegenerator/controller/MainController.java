package com.lld.autocode.codegenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * @description:
 * @author: wzl
 * @date 2021/8/30 15:14
 */
@Controller
public class MainController {

    @GetMapping("/autocode")
    public String autocode(){
        return "/autoCode/autocode";
    }

    @GetMapping("/autoCodeMain")
    public String autocodeMain(){
        return "/autoCode/autoCodeMain";
    }


    @GetMapping("/testhttp")
    @ResponseBody
    public String testhttp(HttpServletRequest req){
        Enumeration<String> headerNames = req.getHeaderNames();
       while(headerNames.hasMoreElements()){
           String headerpart = headerNames.nextElement();
           System.out.println(headerpart+"=="+req.getHeader(headerpart));
       }
//        System.out.println("enter");
        return "123";
    }
}
