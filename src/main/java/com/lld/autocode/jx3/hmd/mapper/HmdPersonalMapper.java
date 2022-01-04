package com.lld.autocode.jx3.hmd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/28 14:22
 */
@Mapper
public interface HmdPersonalMapper extends BaseMapper<HmdPersonal> {

    IPage<HmdPersonal> getAllHmdPersonalWithPage(Page<HmdPersonal> page, @Param("roleName") String roleName,@Param("uid") String uid);

//    List<HmdPersonal> getAllHmdPersonal(@Param("roleName") String roleName,@Param("uid") String uid);

    void addHmdPersonal(HmdPersonal hmdPersonal);

    HmdPersonal selectById(Long id);

    int updateById(HmdPersonal hmdPersonal);

    int deleteById(Long id);
}
