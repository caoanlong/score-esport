package com.dragon.scoreapi.model.csgo;

import lombok.Data;

/**
 * CS:GO 当前赛事战队的统计资料
 */
@Data
public class CsgoTeamStats {
    /**
     * 统计ID
     */
    private Integer teamStatsId;
    /**
     * 赛事ID
     */
    private Integer matchId;
    /**
     * 地图名称
     */
    private String mapName;
    private Integer firstScore;
    private Integer firstWin;
    private Integer fiveWin;
    private Integer overTimeScore;
    private Integer secondScore;
    private Integer sixteenthWin;
    private Integer tenWin;
    /**
     * 阵营：警和匪
     */
    private Integer side;
    private Integer teamId;
}
