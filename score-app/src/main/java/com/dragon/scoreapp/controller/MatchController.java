package com.dragon.scoreapp.controller;

import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.service.MatchService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "gameTypes", defaultValue = "lol,dota,kog,csgo,ow") String gameTypes
    ) throws ParseException {
        Date date = new Date();
        if (null != time) {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        }

        String[] gameTypeArray = gameTypes.split(",");
        List<Match> list = matchService.findList(date, gameTypeArray);
        return ResultUtils.success(list);
    }
}
