package com.lld.autocode.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lld.autocode.system.entity.MenuInfo;
import com.lld.autocode.utils.ReturnMessage;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/7 10:31
 */
public interface SystemService extends IService<MenuInfo> {
    ReturnMessage getRoute();
}
