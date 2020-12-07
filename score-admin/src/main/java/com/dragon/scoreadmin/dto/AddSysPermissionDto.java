package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddSysPermissionDto {
    private Integer pid;
    @NotBlank(message = "权限名不能为空")
    private String perName;
    @NotBlank(message = "权限类型不能为空")
    private String perType;
    private String permission;
    private String url;
    private Integer sort;
}
