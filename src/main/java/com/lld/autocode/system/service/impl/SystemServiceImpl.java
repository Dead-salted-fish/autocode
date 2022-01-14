package com.lld.autocode.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lld.autocode.system.entity.MenuInfo;
import com.lld.autocode.system.entity.TreeNode;
import com.lld.autocode.system.mapper.SystemMapper;
import com.lld.autocode.system.service.SystemService;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/7 10:32
 */
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, MenuInfo> implements SystemService {
    @Override
    public ReturnMessage getRoute() {
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

}
