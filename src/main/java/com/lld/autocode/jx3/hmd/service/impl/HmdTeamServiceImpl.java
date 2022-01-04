package com.lld.autocode.jx3.hmd.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.entity.HmdTeam;
import com.lld.autocode.jx3.hmd.mapper.HmdPersonalMapper;
import com.lld.autocode.jx3.hmd.mapper.HmdTeamMapper;
import com.lld.autocode.jx3.hmd.service.HmdTeamService;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.utils.ReturnMessage;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:12
 */
@Service
public class HmdTeamServiceImpl extends ServiceImpl<HmdTeamMapper, HmdTeam> implements HmdTeamService {

    @Autowired
    private HmdTeamMapper hmdTeamMapper;


    @Override
    public ReturnMessage getAllHmdTeam(Long page, Long pageSize, String teamName, String yyChannel, String qqGroup) {
        Page<HmdTeam> teamPage = new Page<>(page,pageSize);
        IPage<HmdTeam> allHmdTeamWithPage = this.baseMapper.getAllHmdTeamWithPage(teamPage, teamName, yyChannel, qqGroup);
        return ReturnMessage.ok(allHmdTeamWithPage);
    }

    @Override
    public ReturnMessage addHmdTeam(HmdTeam hmdTeam) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hmdTeam.initBase(user.getId());
        hmdTeam.setId(TinyId.nextId("experiment"));
        this.baseMapper.addHmdTeam(hmdTeam);
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage updateHmdTeam(HmdTeam hmdTeam) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HmdTeam oldItem = this.baseMapper.selectById(hmdTeam.getId());
        hmdTeam.updateBase(oldItem.getVersion(),user.getId());
        this.baseMapper.updateById(hmdTeam);
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage deleteHmdTeam(HmdTeam hmdTeam) {
        this.baseMapper.deleteById(hmdTeam.getId());
        return ReturnMessage.ok();
    }
}
