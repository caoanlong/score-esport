package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.SysRole;
import com.dragon.scoreapi.model.SysRolePermission;
import com.dragon.scoreapi.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository {
    List<SysRole> findAll();

    List<SysRole> findList(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize, @Param("roleName") String roleName);

    SysRole findById(Integer id);

    Long total(@Param("roleName") String roleName);

    void insert(SysRole sysRole);

    void update(SysRole sysRole);

    void del(Integer id);

    void insertSysRolePermission(List<SysRolePermission> sysRolePermissions);

    void delSysRolePermissionByRoleId(Integer roleId);

    List<String> findRolesByUserId(Integer userid);

    List<SysRolePermission> findPermissionByRoleId(Integer roleId);
}
