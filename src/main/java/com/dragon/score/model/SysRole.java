package com.dragon.score.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色
 */
@Data
public class SysRole {
    private Integer id;
    private String roleName;
    private List<Integer> permissions = new ArrayList<>();
    private List<SysPermission> sysPermissions = new ArrayList<>();
}
