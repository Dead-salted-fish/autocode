package com.lld.autocode.codegenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description:
 * @author: wzl
 * @date 2021/8/30 15:14
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String autocode(){
        return "login/login";
    }


}
