package com.dragon.scoreapi.model;

import lombok.Data;

@Data
public class DotaTeamStats {
    private Long id;
    private String matchId;
    private Integer assistsCount;
    private Integer dieCount;
    private Integer economicCount;
    private Integer expCount;
    private Integer fifteenKill;
    private Integer firstBlood;
    private Integer firstRouShan;
    private Integer firstTower;
    private Integer fiveKill;
    private Integer killCount;
    private Integer tenKill;
    private Integer towerCount;
    private Integer win;
    private Integer crystalCount;
    private Integer economicDiff;
}
