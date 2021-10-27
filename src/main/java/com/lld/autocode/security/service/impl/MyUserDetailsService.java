package com.lld.autocode.security.service.impl;

import com.lld.autocode.security.entity.User;
import com.lld.autocode.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/26 14:06
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if(null!=user){
            List<String> rolesByUserId = userService.getRolesByUserId(user.getId());
            user.setRoles(rolesByUserId);
        }
        return user;
    }
}
