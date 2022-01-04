package com.lld.autocode.jx3.hmd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.entity.HmdTeam;
import com.lld.autocode.utils.ReturnMessage;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:11
 */

public interface HmdTeamService extends IService<HmdTeam> {


    ReturnMessage getAllHmdTeam(Long page, Long pageSize, String teamName, String yyChannel, String qqGroup);

    ReturnMessage addHmdTeam(HmdTeam hmdTeam);

    ReturnMessage updateHmdTeam(HmdTeam hmdTeam);

    ReturnMessage deleteHmdTeam(HmdTeam hmdTeam);
}
