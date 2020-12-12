package com.dragon.scoreapi.model;

import lombok.Data;

import java.util.Date;

/**
 * 战队
 */
@Data
public class Team {
    private String id;
    private String gameType;
    private Integer version;
    private Boolean deleted;
    private String fullName;
    private String shortName;
    private String nameZh;
    private String nameEn;
    private String abbrZh;
    private String abbrEn;
    private String logo;
    private String logo2;
    private Integer sourceType;
    private Integer countryId;
    private Integer regionId;
    private String regionName;
    private String totalEarnings;
    private String countryNameZh;
    private String countryNameEn;
    private String countryLogo;
    private String countryLogo2;
    private Integer score;
    private Integer ranking;
    private Date createdTime;
    private Date updatedTime;
}
