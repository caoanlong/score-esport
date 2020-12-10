package com.dragon.scoreapi.model.csgo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 玩家当前比赛统计
 */
@Data
public class CsgoPlayerStats {
    /**
     * 玩家当前比赛统计ID
     */
    private Integer playerStatsId;
    /**
     * 金钱
     */
    private BigDecimal money;
    private BigDecimal roundAvgOutput;
    private Integer defuseKit;
    private Integer helmet;
    private Integer kevlar;
    /**
     * 生命值
     */
    private Integer hp;
    /**
     * 击杀数
     */
    private Integer killCount;
    /**
     * 助攻数
     */
    private Integer assistsCount;
    /**
     * 死亡数
     */
    private Integer dieCount;
    /**
     * 装备ID
     */
    private Integer weaponId;
    /**
     * 玩家ID
     */
    private Integer playerId;
}
