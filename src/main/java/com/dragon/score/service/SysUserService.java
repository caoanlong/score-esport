package com.dragon.score.service;

import com.dragon.score.model.SysUser;

import java.util.List;

public interface SysUserService {
    List<SysUser> findAll();
    SysUser findByUsername(String username);
}
