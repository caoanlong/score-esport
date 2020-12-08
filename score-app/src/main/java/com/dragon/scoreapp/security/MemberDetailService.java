package com.dragon.scoreapp.security;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.service.MemberService;
import com.dragon.scoreapi.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class MemberDetailService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        if (StringUtils.isBlank(phone)) {
            log.warn("用户：{} 不存在", phone);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        Member member = memberService.findByPhone(phone);
        if (null == member) {
            log.warn("用户：{} 不存在", phone);
            throw new CommonException(ResCode.USERNAME_NOTFOUNT);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        member.setLoginTime(new Date());
        String ip = IpUtils.getIpAddr(request);
        member.setLoginIp(ip);
        memberService.update(member);
        return member;
    }
}
