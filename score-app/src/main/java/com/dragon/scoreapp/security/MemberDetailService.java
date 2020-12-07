package com.dragon.scoreapp.security;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.service.MemberService;
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
public class MemberDetailService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        if (StringUtils.isBlank(phone)) {
            log.info("用户：{} 不存在", phone);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        Member member = memberService.findByPhone(phone);
        if (null == member) {
            log.info("用户：{} 不存在", phone);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        // member.setLoginIp();
        member.setLoginTime(new Date());
        return member;
    }
}
