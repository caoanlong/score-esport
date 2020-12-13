package com.dragon.scoreapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 联赛
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Tournament {
    private String tournamentId;
    private String tournamentName;
    private String tournamentLogo;
    private String tournamentNameEn;
    private String tournamentShortName;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private String gameType;
}
