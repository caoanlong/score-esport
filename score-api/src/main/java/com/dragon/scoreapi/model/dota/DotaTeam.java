package com.dragon.scoreapi.model.dota;

import lombok.Data;

/**
 * dota 战队
 */
@Data
public class DotaTeam {
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
