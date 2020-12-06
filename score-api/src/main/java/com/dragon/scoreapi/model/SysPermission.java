package com.dragon.scoreapi.model;

import lombok.Data;

import java.util.List;

/**
 * 系统权限
 */
@Data
public class SysPermission {
    private Integer id;
    private Integer pid;
    private String perName;
    private String perType;
    private String permission;
    private String url;
    private Integer sort;
    private List<SysPermission> children;
}
