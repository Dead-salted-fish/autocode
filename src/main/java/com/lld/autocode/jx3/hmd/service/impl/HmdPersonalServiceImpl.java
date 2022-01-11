package com.lld.autocode.jx3.hmd.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.mapper.HmdPersonalMapper;
import com.lld.autocode.jx3.hmd.service.HmdPersonalService;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.utils.ReturnMessage;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:12
 */
@Service
public class HmdPersonalServiceImpl extends ServiceImpl<HmdPersonalMapper, HmdPersonal> implements HmdPersonalService {

    private HmdPersonalMapper hmdPersonalMapper;

    @Override
    public ReturnMessage getAllHmdPersonal(Long page, Long pageSize, String roleName, String uid) {

        Page<HmdPersonal> tablePage = new Page<>(page,pageSize);
        IPage<HmdPersonal> HmdPersonals = this.baseMapper.getAllHmdPersonalWithPage(tablePage, roleName, uid);
        return  ReturnMessage.ok(HmdPersonals);

    }

    @Override
    public ReturnMessage addHmdPersonal(HmdPersonal personal) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        personal.initBase(user.getId());
        personal.setId(TinyId.nextId("experiment"));
        this.baseMapper.addHmdPersonal(personal);
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage updateHmdPersonal(HmdPersonal personal) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HmdPersonal oldItem = this.baseMapper.selectById(personal.getId());
        personal.updateBase(oldItem.getVersion(),user.getId());
        this.baseMapper.updateById(personal);
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage deleteHmdPersonal(HmdPersonal personal) {
        this.baseMapper.deleteById(personal.getId());
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage getById(HmdPersonal personal) {
        HmdPersonal hmdPersonal = this.baseMapper.selectById(personal.getId());
        return ReturnMessage.ok(hmdPersonal);
    }

}
