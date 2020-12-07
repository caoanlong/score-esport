package com.dragon.scoreadmin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdDto {
    @NotNull(message = "ID 不能为空")
    private Integer id;
}
