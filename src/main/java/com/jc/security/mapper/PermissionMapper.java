package com.jc.security.mapper;

import com.jc.security.model.Permission;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface PermissionMapper extends MyMapper<Permission> {
}