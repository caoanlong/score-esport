package com.dragon.scoreapi.service;

import com.dragon.scoreapi.model.SysUser;
import com.dragon.scoreapi.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
