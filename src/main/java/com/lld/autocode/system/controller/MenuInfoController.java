package com.lld.autocode.system.controller;

import com.lld.autocode.system.entity.MenuInfo;
import com.lld.autocode.system.service.MenuInfoService;
import com.lld.autocode.utils.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/14 10:47
 */
@RestController
@RequestMapping("system")
public class MenuInfoController {
    @Autowired
    private MenuInfoService menuInfoService;

    //左侧菜单栏
    @GetMapping("/menu/getMenus")
    public ReturnMessage getMenus(HttpServletRequest req){

        return menuInfoService.getMenus();
    }

    //菜单管理列表
    @GetMapping("/menu/getMenusList")
    public ReturnMessage getMenusList(HttpServletRequest req){
        return menuInfoService.getMenusList();
    }

    //菜单管理抽屉父菜单树
    @GetMapping("/menu/getParentMenusTree")
    public ReturnMessage getParentMenusTree(HttpServletRequest req){
        return menuInfoService.getParentMenusTree();
    }

    //菜单管理抽屉父菜单树
    @PostMapping("/menu/addMenu")
    public ReturnMessage addMenu(@RequestBody MenuInfo menuInfo, HttpServletRequest req){
        return menuInfoService.addMenu(menuInfo);
    }

    //删除菜单
    @PostMapping("/menu/deleteMenu")
    public ReturnMessage deleteMenu(@RequestBody MenuInfo menuInfo, HttpServletRequest req){
        return menuInfoService.deleteMenu(menuInfo);
    }

    //更新菜单
    @PostMapping("/menu/updateMenu")
    public ReturnMessage updateMenu(@RequestBody MenuInfo menuInfo, HttpServletRequest req){
        return menuInfoService.updateMenu(menuInfo);
    }


}
