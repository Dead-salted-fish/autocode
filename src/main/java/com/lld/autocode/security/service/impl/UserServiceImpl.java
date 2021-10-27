package com.lld.autocode.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lld.autocode.security.entity.User;
import com.lld.autocode.security.mapper.UserMapper;
import com.lld.autocode.security.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return this.baseMapper.getUserByUsername(username);
    }

    @Override
    public List<String> getRolesByUserId(Long id) {
        return this.baseMapper.getRolesByUserId(id);
    }
}
