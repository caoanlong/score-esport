package com.dragon.scoreadmin.controller;

import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.service.MemberService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<Member> all = memberService.findAll();
        return ResultUtils.success(all);
    }
}
