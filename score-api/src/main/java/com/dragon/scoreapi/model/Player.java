package com.dragon.scoreapi.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Player {
    private String id;
    private Integer version;
    private String gameType;
    private Boolean deleted;
    private String fullName;
    private String shortName;
    private String nameZh;
    private String nameEn;
    private String abbrZh;
    private String abbrEn;
    private String logo;
    private String logo2;
    private String teamId;
    private Integer countryId;
    private String realName;
    private Date birthday;
    private Integer retired;
    private String teamNameZh;
    private String teamNameEn;
    private String teamLogo;
    private String teamLogo2;
    private String countryNameZh;
    private String countryNameEn;
    private String countryLogo;
    private String countryLogo2;
    private String positionName;
    private String startOrderName;
    private Date createdTime;
    private Date updatedTime;
}
