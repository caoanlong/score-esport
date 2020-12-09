package com.dragon.scoreapi.service;

import com.dragon.scoreapi.model.PageBean;
import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.model.SysUserRole;
import com.dragon.scoreapi.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public PageBean<List<SysUser>> findList(Integer pageIndex, Integer pageSize, String username) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysUser> list = sysUserRepository.findList(username, pageStart, pageSize);
        Long total = sysUserRepository.total(username);
        PageBean<List<SysUser>> pageBean = new PageBean<>();
        pageBean.setList(list);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    ;

    public SysUser findById(Integer id) {
        return sysUserRepository.findById(id);
    }

    @Transactional
    public void insert(SysUser sysUser) {
        Date now = new Date();
        sysUser.setCreateTime(now);
        sysUser.setUpdateTime(now);
        sysUserRepository.insert(sysUser);
        List<Integer> roleIds = sysUser.getRoleIds();
        if (null == roleIds || roleIds.size() == 0) return;
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Integer roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRepository.insertSysUserRole(sysUserRoles);
    }

    @Transactional
    public void update(SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
        sysUserRepository.update(sysUser);
        Integer id = sysUser.getId();
        sysUserRepository.delSysUserRoleByUserId(id);
        List<Integer> roleIds = sysUser.getRoleIds();
        if (null == roleIds || roleIds.size() == 0) return;
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Integer roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRepository.insertSysUserRole(sysUserRoles);
    }

    @Transactional
    public void del(Integer id) {
        sysUserRepository.del(id);
        sysUserRepository.delSysUserRoleByUserId(id);
    }
}
