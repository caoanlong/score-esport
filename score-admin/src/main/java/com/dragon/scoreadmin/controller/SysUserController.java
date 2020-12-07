package com.dragon.scoreadmin.controller;

import com.dragon.scoreadmin.dto.AddSysUserDto;
import com.dragon.scoreadmin.dto.IdDto;
import com.dragon.scoreadmin.dto.UpdateSysUserDto;
import com.dragon.scoreapi.model.PageBean;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.service.SysUserService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", required = false) String username
    ) {
        PageBean<List<SysUser>> list = sysUserService.findList(pageIndex, pageSize, username);
        return ResultUtils.success(list);
    }

    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Integer id) {
        SysUser sysUser = sysUserService.findById(id);
        return ResultUtils.success(sysUser);
    }

    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody @Validated AddSysUserDto dto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUserService.insert(sysUser);
        return ResultUtils.success();
    }

    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody @Validated UpdateSysUserDto dto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUserService.update(sysUser);
        return ResultUtils.success();
    }

    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @Validated IdDto dto) {
        sysUserService.del(dto.getId());
        return ResultUtils.success();
    }
}
