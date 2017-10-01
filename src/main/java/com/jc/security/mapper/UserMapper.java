package com.jc.security.mapper;

import com.jc.security.model.User;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends MyMapper<User> {
}