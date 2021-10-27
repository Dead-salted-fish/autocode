package com.lld.autocode.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lld.autocode.security.entity.User;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:11
 */

public interface UserService extends IService<User> {
    User getUserByUsername(String username);
    List<String> getRolesByUserId(Long id);
}
