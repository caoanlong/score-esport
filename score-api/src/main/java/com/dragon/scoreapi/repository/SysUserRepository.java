package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository {
    List<SysUser> findAll();

    SysUser findByUsername(String username);
}
