package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddSysRoleDto {
    @NotBlank(message = "角色名不能为空")
    private String roleName;
    private List<Integer> permissions = new ArrayList<>();
}
