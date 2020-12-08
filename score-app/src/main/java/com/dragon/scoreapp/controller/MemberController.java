package com.dragon.scoreapp.controller;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.service.MemberService;
import com.dragon.scoreapi.utils.Constants;
import com.dragon.scoreapi.utils.RandomCodeUtils;
import com.dragon.scoreapi.utils.ResultUtils;
import com.dragon.scoreapp.dto.RegisterMemberDto;
import com.dragon.scoreapp.security.MemberDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/test")
    public ResultBean<Object> test() {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultUtils.success(id);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public ResultBean<Object> info() {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findById(id);
        return ResultUtils.success(member);
    }

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/getPhoneCode")
    public ResultBean<Object> getPhoneCode(String phone) {
        if (StringUtils.isEmpty(phone)) throw new CommonException(ResCode.PHONE_NOTNULL);
        if (!Pattern.matches(Constants.REGEX_MOBILE, phone)) throw new CommonException(ResCode.PHONE_ERROR);
        String code = RandomCodeUtils.create(4);
        stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        return ResultUtils.success(code);
    }

    /**
     * 注册
     * @param response
     * @param dto
     * @return
     */
    @PostMapping("/register")
    @Transactional
    public ResultBean<Object> register(
            HttpServletResponse response,
            @RequestBody @Validated RegisterMemberDto dto
    ) {
        String username = dto.getUsername();
        String phone = dto.getPhone();
        String password = dto.getPassword().trim();
        String code = stringRedisTemplate.opsForValue().get(phone);
        if (!dto.getCode().equals(code)) throw new CommonException(ResCode.CODE_ERROR);
        Member member = new Member();
        member.setUsername(username);
        member.setPhone(phone);
        member.setPassword(passwordEncoder.encode(password));
        memberService.insert(member);

        String token = Jwts.builder()
                .claim("authorities", new ArrayList<>())
                .claim("phone", phone)
                .claim("username", member.getUsername())
                .claim("id", member.getId())
                .setSubject(member.getPhone())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.SCERET_KEY)
                .compact();

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", token);
        stringRedisTemplate.delete(phone);
        return ResultUtils.success();
    }
}
