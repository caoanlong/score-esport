package com.dragon.scoreapp.controller;

import com.dragon.scoreapi.model.ResultBean;
import com.dragon.scoreapi.model.Tournament;
import com.dragon.scoreapi.service.TournamentService;
import com.dragon.scoreapi.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "gameType") String gameType,
            @RequestParam(value = "status") Integer status
    ) {
        List<Tournament> list = tournamentService.findList(gameType, status);
        return ResultUtils.success(list);
    }

    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam(value = "id") String id) {
        Tournament tournament = tournamentService.findById(id);
        return ResultUtils.success(tournament);
    }
}
