package com.dragon.scoreapp.security;

import com.dragon.scoreapi.utils.Constants;
import com.dragon.scoreapi.utils.ResultUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@Slf4j
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        if (null == authorization) {
            chain.doFilter(request, response);
            return;
        }
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parser().setSigningKey(Constants.SCERET_KEY).parseClaimsJws(authorization.replace("Bearer ", ""));
        } catch (ExpiredJwtException e) {
            log.error("token过期: {}", e.getMessage());
            HttpServletResponse res = (HttpServletResponse) response;
            res.setContentType("application/json;charset=utf-8");
            PrintWriter out = res.getWriter();
            out.write(ResultUtils.err(1001, "token已过期"));
            out.flush();
            out.close();
            return;
        } catch (JwtException e) {
            log.error("token错误: {}", e.getMessage());
            HttpServletResponse res = (HttpServletResponse) response;
            res.setContentType("application/json;charset=utf-8");
            PrintWriter out = res.getWriter();
            out.write(ResultUtils.err(1001, "token错误: " + e.getMessage()));
            out.flush();
            out.close();
            return;
        }
        Claims claims = jws.getBody();
        Object id = claims.get("id");
        Object phone = claims.get("phone");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, phone, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
