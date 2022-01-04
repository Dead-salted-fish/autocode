package com.lld.autocode.jx3.hmd.controller;

import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.service.HmdPersonalService;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/28 14:20
 */
@RestController
public class HmdPersonalController {

    @Autowired
    private HmdPersonalService hmdPersonalService;

    @GetMapping("/jx3/hmdPersonal/list")
    public ReturnMessage getAllHmdPersonal(Long page,
                                         Long pageSize,
                                         String roleName,
                                         String uid
                                         ){
        return hmdPersonalService.getAllHmdPersonal(page, pageSize,roleName,uid);
    }

    @PostMapping("/jx3/hmdPersonal/add")
    public ReturnMessage addHmdPersonal(@RequestBody HmdPersonal personal){
        return hmdPersonalService.addHmdPersonal(personal);
    }

    @PostMapping("/jx3/hmdPersonal/update")
    public ReturnMessage updateHmdPersonal(@RequestBody HmdPersonal personal){
        return hmdPersonalService.updateHmdPersonal(personal);
    }

    @PostMapping("/jx3/hmdPersonal/delete")
    public ReturnMessage deleteHmdPersonal(@RequestBody HmdPersonal personal){
        return hmdPersonalService.deleteHmdPersonal(personal);
    }

}
