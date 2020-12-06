package com.dragon.score.controller.app;

import com.dragon.score.model.Member;
import com.dragon.score.model.ResultBean;
import com.dragon.score.service.MemberService;
import com.dragon.score.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/member")
public class AppMemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<Member> all = memberService.findAll();
        return ResultUtils.success(all);
    }
}
