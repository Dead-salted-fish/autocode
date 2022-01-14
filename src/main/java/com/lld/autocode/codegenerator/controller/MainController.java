package com.lld.autocode.codegenerator.controller;

import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.utils.WebSocketServer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/autocode")
    public String autocode(){
        return "/autoCode/autocode";
    }

    @GetMapping("/autoCodeMain")
    public String autocodeMain(){
        return "/autoCode/autoCodeMain";
    }

    @GetMapping("/main")
    public String main(){
        return "/main/main";
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

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest req){

//        webSocketServer.sendInfo(1L,"6666666666");
        HmdPersonal hmdPersonal = new HmdPersonal();
        hmdPersonal.setId(1L);

        rabbitTemplate.convertAndSend("testexchange" ,"testkey",hmdPersonal);
        return "App";
    }

    @GetMapping("/test1")
    @ResponseBody
    public String test1(HttpServletRequest req){

//        webSocketServer.sendInfo(1L,"6666666666");
        HmdPersonal hmdPersonal = new HmdPersonal();
        hmdPersonal.setId(2L);
        rabbitTemplate.convertAndSend("testexchange" ,"testkey1",hmdPersonal);
        return "App";
    }


}
