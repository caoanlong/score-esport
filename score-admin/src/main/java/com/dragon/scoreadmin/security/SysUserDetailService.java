package com.dragon.scoreadmin.security;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.service.SysPermissionService;
import com.dragon.scoreapi.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SysUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.info("用户：{} 不存在", username);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        SysUser sysUser = sysUserService.findByUsername(username);
        if (null == sysUser) {
            log.info("用户：{} 不存在", username);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        // sysUser.setLoginIp();
        sysUser.setLoginTime(new Date());
        List<String> permissions = sysPermissionService.findPermissionByUserId(sysUser.getId());
        List<GrantedAuthority> authorities = permissions.stream().map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toList());
        sysUser.setAuthorities(authorities);
        return sysUser;
    }
}
