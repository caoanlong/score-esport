package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateSysPermissionDto {
    private Integer id;
    private Integer pid;
    private String perName;
    private String perType;
    private String permission;
    private String url;
    private Integer sort;
}
