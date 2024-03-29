package com.dragon.scoreapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Match {
    private String matchId;
    private Date matchTime;
    private String matchTitle;
    private String gameType;
    private Integer status;
    private String tournamentId;
    private String tournamentLogo;
    private String tournamentName;
    private String tournamentNameEn;
    private String tournamentShortName;
    private Integer viewActualNum;
    private Integer viewNum;
    private Integer lengthTime;
    private Boolean isFocus;
    private Integer box;
    private Integer boxBum;

    private String homeId;
    private String homeName;
    private String homeShortName;
    private String homeLogo;
    private Integer homeLiveScore;
    private Integer homeScore;
    private String homeOdds;
    private String homeRangFen;
    private String homeTrend;

    private String awayId;
    private String awayName;
    private String awayShortName;
    private String awayLogo;
    private Integer awayLiveScore;
    private Integer awayScore;
    private String awayOdds;
    private String awayRangFen;
    private String awayTrend;

    private Team home;
    private Team away;
}
