package com.lld.autocode.system.controller;

import com.lld.autocode.system.service.SystemService;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/7 10:26
 */
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/system/getRoute")
    public ReturnMessage getRoute(HttpServletRequest req){

        return systemService.getRoute();
    }
}
