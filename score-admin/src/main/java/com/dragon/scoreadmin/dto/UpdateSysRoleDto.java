package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateSysRoleDto {
    @NotNull(message = "ID 不能为空")
    private Integer id;
    private String roleName;
    private List<Integer> permissions = new ArrayList<>();
}
