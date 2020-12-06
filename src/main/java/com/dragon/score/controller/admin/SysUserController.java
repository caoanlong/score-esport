package com.dragon.score.controller.admin;

import com.dragon.score.model.ResultBean;
import com.dragon.score.model.SysUser;
import com.dragon.score.service.SysUserService;
import com.dragon.score.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysUser> all = sysUserService.findAll();
        return ResultUtils.success(all);
    }
}
