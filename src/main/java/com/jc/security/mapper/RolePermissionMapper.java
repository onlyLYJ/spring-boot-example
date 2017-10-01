package com.jc.security.mapper;

import com.jc.security.model.Permission;
import com.jc.security.model.RolePermission;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RolePermissionMapper extends MyMapper<RolePermission> {
    List<Permission> findPermissionByUserId(Integer userId);
}