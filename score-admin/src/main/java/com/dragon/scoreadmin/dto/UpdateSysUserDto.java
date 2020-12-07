package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateSysUserDto {
    @NotNull(message = "ID 不能为空")
    private Integer id;
    private String username;
    private String password;
    private List<Integer> roleIds = new ArrayList<>();
}
