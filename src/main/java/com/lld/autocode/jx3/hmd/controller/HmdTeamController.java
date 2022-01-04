package com.lld.autocode.jx3.hmd.controller;

import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.entity.HmdTeam;
import com.lld.autocode.jx3.hmd.service.HmdTeamService;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/28 14:20
 */
@RestController
public class HmdTeamController {

@Autowired
    private HmdTeamService hmdTeamService;

    @GetMapping("/jx3/hmdTeam/list")
    public ReturnMessage getAllHmdTeam(    Long page,
                                           Long pageSize,
                                           String teamName,
                                           String yyChannel,
                                           String qqGroup
    ){
        return hmdTeamService.getAllHmdTeam(page, pageSize,teamName,yyChannel,qqGroup);
    }

    @PostMapping("/jx3/hmdTeam/add")
    public ReturnMessage addHmdTeam(@RequestBody HmdTeam hmdTeam){
        return hmdTeamService.addHmdTeam(hmdTeam);
    }

    @PostMapping("/jx3/hmdTeam/update")
    public ReturnMessage updateHmdTeam(@RequestBody HmdTeam hmdTeam){
        return hmdTeamService.updateHmdTeam(hmdTeam);
    }

    @PostMapping("/jx3/hmdTeam/delete")
    public ReturnMessage deleteHmdTeam(@RequestBody HmdTeam hmdTeam){
        return hmdTeamService.deleteHmdTeam(hmdTeam);
    }

}
