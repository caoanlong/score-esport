package com.dragon.score.repository;

import com.dragon.score.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository {
    List<SysUser> findAll();

    SysUser findByUsername(String username);
}
