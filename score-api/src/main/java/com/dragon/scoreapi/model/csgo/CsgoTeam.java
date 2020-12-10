package com.dragon.scoreapi.model.csgo;

import lombok.Data;

/**
 * CS:GO 战队
 */
@Data
public class CsgoTeam {
    /**
     * 战队ID
     */
    private Integer teamId;
    /**
     * 战队名称
     */
    private String teamName;
    /**
     * 战队LOGO
     */
    private String teamLogo;

    private String teamNameEn;
    private String teamNameZh;
}
