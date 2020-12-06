package com.dragon.scoreadmin.controller;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.service.SysUserService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysUser> all = sysUserService.findAll();
        return ResultUtils.success(all);
    }
}
