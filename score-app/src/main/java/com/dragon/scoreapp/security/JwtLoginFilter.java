package com.dragon.scoreapp.security;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.utils.Constants;
import com.dragon.scoreapi.utils.ResultUtils;
import com.dragon.scoreapp.dto.LoginMemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        LoginMemberDto member = new ObjectMapper().readValue(req.getInputStream(), LoginMemberDto.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(member.getPhone(), member.getPassword()));
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = new Member();
        BeanUtils.copyProperties(authResult.getPrincipal(), member);
        String token = Jwts.builder()
                .claim("authorities", member.getAuthorities())
                .claim("phone", member.getPhone())
                .claim("username", member.getUsername())
                .claim("id", member.getId())
                .setSubject(member.getPhone())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.SCERET_KEY)
                .compact();
        res.setContentType("application/json;charset=utf-8");
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader("Authorization", token);
        PrintWriter out = res.getWriter();
        out.write(ResultUtils.ok());
        out.flush();
        out.close();
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        res.setContentType("application/json;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.write(ResultUtils.err(ResCode.USER_PASS_ERROR));
        out.flush();
        out.close();
    }
}
