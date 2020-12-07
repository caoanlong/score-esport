package com.dragon.scoreapp.controller;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.service.MemberService;
import com.dragon.scoreapi.utils.JwtUtils;
import com.dragon.scoreapi.utils.RandomCodeUtils;
import com.dragon.scoreapi.utils.ResultUtils;
import com.dragon.scoreapp.dto.LoginMemberDto;
import com.dragon.scoreapp.dto.RegisterMemberDto;
import com.dragon.scoreapp.security.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/getPhoneCode")
    public ResultBean<Object> getPhoneCode(String phone) {
        if (phone == null) throw new CommonException(ResCode.PHONE_NOTNULL);
        String code = RandomCodeUtils.create(4);
        stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        return ResultUtils.success(code);
    }

    @PostMapping("/register")
    @Transactional
    public ResultBean<Object> register(
            HttpServletResponse response,
            @RequestBody @Validated RegisterMemberDto dto
    ) {
        String phone = dto.getPhone();
        String password = dto.getPassword().trim();
        String code = stringRedisTemplate.opsForValue().get(phone);
        if (!dto.getCode().equals(code)) throw new CommonException(ResCode.CODE_ERROR);
        Member member = new Member();
        member.setPhone(phone);
        member.setPassword(passwordEncoder.encode(password));
        memberService.insert(member);

        UserDetails userDetails = memberDetailService.loadUserByUsername(phone);
        String token = jwtUtils.generateToken(userDetails);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", token);
        return ResultUtils.success();
    }

    @PostMapping("/login")
    @Transactional
    public ResultBean<Object> login(
            HttpServletResponse response,
            @RequestBody @Validated LoginMemberDto dto
    ) {
        String phone = dto.getPhone();
        String password = dto.getPassword().trim();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));

        UserDetails userDetails = memberDetailService.loadUserByUsername(phone);
        String token = jwtUtils.generateToken(userDetails);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", token);
        return ResultUtils.success();
    }
}
