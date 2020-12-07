package com.dragon.scoreadmin.controller;

import com.dragon.scoreadmin.dto.*;
import com.dragon.scoreapi.model.PageBean;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.SysPermission;
import com.dragon.scoreapi.model.SysRole;
import com.dragon.scoreapi.service.SysPermissionService;
import com.dragon.scoreapi.service.SysRoleService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<SysPermission> all = sysPermissionService.findAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "perName", required = false) String perName,
            @RequestParam(value = "perType", required = false) String perType
    ) {
        PageBean<List<SysPermission>> list = sysPermissionService.findList(pageIndex, pageSize, perName, perType);
        return ResultUtils.success(list);
    }

    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Integer id) {
        SysPermission sysPermission = sysPermissionService.findById(id);
        return ResultUtils.success(sysPermission);
    }

    @GetMapping("/findByPid")
    public ResultBean<Object> findByPid(@RequestParam(value = "pid", required = false) Integer pid) {
        if (null == pid) pid = -1;
        List<SysPermission> sysPermissions = sysPermissionService.findPermissionByPid(pid);
        return ResultUtils.success(sysPermissions);
    }

    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody @Valid AddSysPermissionDto dto) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        sysPermissionService.insert(sysPermission);
        return ResultUtils.success();
    }

    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody @Valid UpdateSysPermissionDto dto) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        sysPermissionService.update(sysPermission);
        return ResultUtils.success();
    }

    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @Valid IdDto dto) {
        sysPermissionService.del(dto.getId());
        return ResultUtils.success();
    }
}
