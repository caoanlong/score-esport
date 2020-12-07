package com.dragon.scoreadmin.controller;

import com.dragon.scoreadmin.dto.*;
import com.dragon.scoreapi.model.PageBean;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.SysRole;
import com.dragon.scoreapi.service.SysRoleService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysRole> all = sysRoleService.findAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "roleName", required = false) String roleName
    ) {
        PageBean<List<SysRole>> list = sysRoleService.findList(pageIndex, pageSize, roleName);
        return ResultUtils.success(list);
    }

    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Integer id) {
        SysRole sysRole = sysRoleService.findById(id);
        return ResultUtils.success(sysRole);
    }

    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody @Valid AddSysRoleDto dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleService.insert(sysRole);
        return ResultUtils.success();
    }

    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody @Valid UpdateSysRoleDto dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRoleService.update(sysRole);
        return ResultUtils.success();
    }

    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @Valid IdDto dto) {
        sysRoleService.del(dto.getId());
        return ResultUtils.success();
    }
}
