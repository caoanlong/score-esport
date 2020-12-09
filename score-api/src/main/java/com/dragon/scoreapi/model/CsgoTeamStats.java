package com.dragon.scoreapi.model;

import lombok.Data;

@Data
public class CsgoTeamStats {
    private Long id;
    private String matchId;
    private String mapName;
    private Integer firstScore;
    private Integer firstWin;
    private Integer fiveWin;
    private Integer overTimeScore;
    private Integer secondScore;
    private Integer sixteenthWin;
    private Integer tenWin;
}
