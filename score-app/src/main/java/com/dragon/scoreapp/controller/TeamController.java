package com.dragon.scoreapp.controller;

import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.Team;
import com.dragon.scoreapi.service.TeamService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "gameType") String gameType
    ) {
        List<Team> list = teamService.findList(gameType);
        return ResultUtils.success(list);
    }
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") String id) {
        Team team = teamService.findById(id);
        return ResultUtils.success(team);
    }
}
