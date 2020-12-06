package com.dragon.score.security;

import com.dragon.score.enums.ResCode;
import com.dragon.score.model.SysUser;
import com.dragon.score.model.exception.CommonException;
import com.dragon.score.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SysUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

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
        return sysUser;
    }
}
