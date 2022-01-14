package com.lld.autocode.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.system.entity.MenuInfo;
import com.lld.autocode.system.entity.TreeNode;
import com.lld.autocode.system.mapper.MenuInfoMapper;
import com.lld.autocode.system.service.MenuInfoService;
import com.lld.autocode.utils.ReturnMessage;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/7 10:32
 */
@Service
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo> implements MenuInfoService {

    @Override
    public ReturnMessage getMenus() {
        List<MenuInfo> baseMenus = this.baseMapper.getBaseMenu();
        setMenuChildren(baseMenus);
        return ReturnMessage.ok(baseMenus);
    }

    @Override
    public ReturnMessage addMenu(MenuInfo menuInfo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        menuInfo.initBase(user.getId());
        menuInfo.setId(TinyId.nextId("experiment"));
        this.baseMapper.addMenu(menuInfo);
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage deleteMenu(MenuInfo menuInfo) {
        this.baseMapper.deleteById(menuInfo.getId());
        return ReturnMessage.ok();
    }

    @Override
    public ReturnMessage getMenusList() {
        List<MenuInfo> baseMenus = this.baseMapper.getBaseMenu();
        setMenuChildren(baseMenus);
        return ReturnMessage.ok(baseMenus);
    }

    private void setMenuChildren(List<MenuInfo> baseMenus){
        for(MenuInfo menuInfo :baseMenus){
            List<MenuInfo> menuChildren = this.baseMapper.getMenuChildren(menuInfo.getId());
            if(menuChildren.size()>0||(menuInfo.getRouterPath()==null||menuInfo.getRouterPath().trim().equals(""))){
                menuInfo.setChildren(menuChildren);
                setMenuChildren(menuChildren);
            }
        }
    }

    @Override
    public ReturnMessage getParentMenusTree() {
        List<TreeNode> parentMenusTree = this.baseMapper.getParentMenusTree();
        setTreeChildren(parentMenusTree);
        return ReturnMessage.ok(parentMenusTree);
    }


    private void setTreeChildren(List<TreeNode> parentMenusTree){
        for(TreeNode treeNode :parentMenusTree){
            List<TreeNode> treeChildren = this.baseMapper.getTreeChildren(treeNode.getKey());
            if(treeChildren.size()>0){
                treeNode.setChildren(treeChildren);
                setTreeChildren(treeChildren);
            }
        }
    }


}
