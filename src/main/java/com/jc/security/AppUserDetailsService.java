package com.jc.security;

import com.google.common.collect.Lists;
import com.jc.model.Employee;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.service.EmployeeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasonzhu on 2017/7/21.
 */
@Data
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String englishName) throws UsernameNotFoundException {
        Employee employee = employeeService.findByEnglishName(englishName);

        if (employee == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        List<Role> roleList = employeeService.findRoleByEmployeeId(employee.getId());
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<Permission> permissionList = employeeService.findPermissionByEmployeeId(employee.getId());
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        permissionList.stream().forEach((permission) -> {
            if (permission != null && permission.getName() != null) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
            }
        });

        return new org.springframework.security.core.userdetails.User(employee.getEnglishName(), employee.getPassword(), grantedAuthorities);
    }
}
