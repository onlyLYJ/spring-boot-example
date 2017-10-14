package com.jc.service.impl;

import com.jc.security.mapper.RoleMapper;
import com.jc.security.model.Role;
import com.jc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Role 权限Service
 * Create by onlyLYJ on 2017/10/09
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRole() {
        return roleMapper.selectAll();
    }


}
