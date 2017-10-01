package com.jc.security.mapper;

import com.jc.security.model.Role;
import com.jc.security.model.UserRole;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRoleMapper extends MyMapper<UserRole> {
    List<Role> findRoleByUserId(Integer userId);
}