package com.lld.autocode.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lld.autocode.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserByUsername(String username);
    List<String> getRolesByUserId(Long id);
}
