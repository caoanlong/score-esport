package com.dragon.scoreapi.service;

import com.dragon.scoreapi.model.PageBean;
import com.dragon.scoreapi.model.SysPermission;
import com.dragon.scoreapi.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    public List<SysPermission> findAll() {
        return sysPermissionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PageBean<List<SysPermission>> findList(Integer pageIndex, Integer pageSize, String perName, String perType) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysPermission> list = sysPermissionRepository.findList(pageStart, pageSize, perName, perType);
        Long total = sysPermissionRepository.total(perName, perType);
        PageBean<List<SysPermission>> pageBean = new PageBean<>();
        pageBean.setList(list);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    ;

    public SysPermission findById(Integer id) {
        return sysPermissionRepository.findById(id);
    }

    public void insert(SysPermission sysPermission) {
        if (null == sysPermission.getPid()) sysPermission.setPid(-1);
        sysPermissionRepository.insert(sysPermission);
    }

    public void update(SysPermission sysPermission) {
        sysPermissionRepository.update(sysPermission);
    }

    public void del(Integer id) {
        sysPermissionRepository.del(id);
    }

    public List<String> findPermissionByUserId(Integer userId) {
        return sysPermissionRepository.findPermissionByUserId(userId);
    }

    public List<SysPermission> findPermissionByPid(Integer pid) {
        return sysPermissionRepository.findPermissionByPid(pid);
    }
}
