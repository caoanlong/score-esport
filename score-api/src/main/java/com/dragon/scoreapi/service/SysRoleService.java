package com.dragon.scoreapi.service;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.*;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.repository.SysRoleRepository;
import com.dragon.scoreapi.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    public List<SysRole> findAll() {
        return sysRoleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PageBean<List<SysRole>> findList(Integer pageIndex, Integer pageSize, String roleName) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysRole> list = sysRoleRepository.findList(pageStart, pageSize, roleName);
        Long total = sysRoleRepository.total(roleName);
        PageBean<List<SysRole>> pageBean = new PageBean<>();
        pageBean.setList(list);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    ;

    public SysRole findById(Integer id) {
        SysRole sysRole = sysRoleRepository.findById(id);
        if (null == sysRole) throw new CommonException(ResCode.DATA_NOT_FOUND);
        return sysRole;
    }

    @Transactional
    public void insert(SysRole sysRole) {
        sysRoleRepository.insert(sysRole);
        Integer id = sysRole.getId();
        List<Integer> permissions = sysRole.getPermissions();
        if (null == permissions || permissions.size() == 0) return;
        List<SysRolePermission> sysRolePermissions = new ArrayList<>();
        for (Integer permission : permissions) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(id);
            sysRolePermission.setPermissionId(permission);
            sysRolePermissions.add(sysRolePermission);
        }
        sysRoleRepository.insertSysRolePermission(sysRolePermissions);
    }

    @Transactional
    public void update(SysRole sysRole) {
        sysRoleRepository.update(sysRole);
        Integer id = sysRole.getId();
        sysRoleRepository.delSysRolePermissionByRoleId(id);
        List<Integer> permissions = sysRole.getPermissions();
        if (null == permissions || permissions.size() == 0) return;
        List<SysRolePermission> sysRolePermissions = new ArrayList<>();
        for (Integer permission : permissions) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(id);
            sysRolePermission.setPermissionId(permission);
            sysRolePermissions.add(sysRolePermission);
        }
        sysRoleRepository.insertSysRolePermission(sysRolePermissions);
    }

    @Transactional
    public void del(Integer id) {
        sysRoleRepository.del(id);
        sysRoleRepository.delSysRolePermissionByRoleId(id);
    }
}
