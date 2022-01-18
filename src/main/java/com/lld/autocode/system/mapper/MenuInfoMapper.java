package com.lld.autocode.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lld.autocode.system.entity.MenuInfo;
import com.lld.autocode.system.entity.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:13
 */
@Mapper
public interface MenuInfoMapper extends BaseMapper<MenuInfo> {
    List<MenuInfo> getMenusWithoutBan();

    List<MenuInfo> getMenusIncludeBan();

    List<MenuInfo>   getMenuChildren(@Param("parentId") Long id);

    List<MenuInfo>   getMenuListChildren(@Param("parentId") Long id);

    List<TreeNode> getParentMenusTree();

    List<TreeNode>   getTreeChildren(@Param("parentId") Long id);

    void addMenu(MenuInfo menuInfo);

    int deleteById(Long id);

    MenuInfo selectById(Long id);

   int updateMenuById(MenuInfo menuInfo);
}
