package com.lld.autocode.jx3.hmd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.jx3.hmd.entity.HmdTeam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @description:
 * @author: wzl
 * @date 2021/12/28 14:22
 */
@Mapper
public interface HmdTeamMapper extends BaseMapper<HmdTeam> {
    IPage<HmdTeam> getAllHmdTeamWithPage(Page<HmdTeam> page,
                                         @Param("teamName") String teanName,
                                         @Param("yyChannel") String yyChannel,
                                         @Param("qqGroup") String qqGroup);

    void addHmdTeam(HmdTeam hmdTeam);

    HmdTeam selectById(Long id);

    int updateById(HmdTeam hmdTeam);

    int deleteById(Long id);
}
