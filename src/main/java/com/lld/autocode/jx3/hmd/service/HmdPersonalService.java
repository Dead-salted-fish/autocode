package com.lld.autocode.jx3.hmd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.utils.ReturnMessage;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:11
 */

public interface HmdPersonalService extends IService<HmdPersonal> {

    ReturnMessage getAllHmdPersonal(Long page, Long pageSize, String roleName, String uid);

    ReturnMessage addHmdPersonal(HmdPersonal personal);

    ReturnMessage updateHmdPersonal(HmdPersonal personal);

    ReturnMessage deleteHmdPersonal(HmdPersonal personal);

    ReturnMessage getById(HmdPersonal personal);
}
