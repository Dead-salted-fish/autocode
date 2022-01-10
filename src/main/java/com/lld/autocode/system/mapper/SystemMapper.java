package com.lld.autocode.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lld.autocode.system.entity.MenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:13
 */
@Mapper
public interface SystemMapper extends BaseMapper<MenuInfo> {

List<MenuInfo> getBaseMenu();

    List<MenuInfo>   getMenuChildren(@Param("parentId") Long id);
}
