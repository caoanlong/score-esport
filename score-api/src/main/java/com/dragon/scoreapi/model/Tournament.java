package com.dragon.scoreapi.model;

import lombok.Data;

import java.util.Date;

/**
 * 联赛
 */
@Data
public class Tournament {
    private Integer tournamentId;
    private String tournamentName;
    private String tournamentLogo;
    private String tournamentNameEn;
    private String tournamentShortName;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private String gameType;
}
