package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository {
    SysUser findByUsername(String username);

    List<SysUser> findAll();

    List<SysUser> findList(@Param("username") String username, @Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    SysUser findById(Integer id);

    Long total(@Param("username") String username);

    void insert(SysUser sysUser);

    void update(SysUser sysUser);

    void del(Integer id);

    void insertSysUserRole(List<SysUserRole> sysUserRoles);

    void delSysUserRoleByUserId(Integer userId);
}
