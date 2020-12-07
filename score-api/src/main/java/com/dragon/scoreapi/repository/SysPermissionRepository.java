package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionRepository {
    List<SysPermission> findAll();

    List<SysPermission> findList(
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize,
            @Param("perName") String perName,
            @Param("perType") String perType
    );

    SysPermission findById(Integer id);

    Long total(@Param("perName") String perName, @Param("perType") String perType);

    void insert(SysPermission sysPermission);

    void update(SysPermission sysPermission);

    void del(Integer id);

    List<String> findPermissionByUserId(Integer userId);

    List<SysPermission> findPermissionByPid(Integer pid);
}
