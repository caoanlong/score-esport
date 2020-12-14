package com.dragon.scoreapi.service;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Team;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
    public List<Team> findList(String gameType) {
        return teamRepository.findList(gameType);
    }

    public Team findById(String id) {
        Team team = teamRepository.findById(id);
        if (null == team) throw new CommonException(ResCode.DATA_NOT_FOUND);
        return team;
    }

    @Transactional
    public void save(Team team) {
        Integer exist = teamRepository.isExist(team.getId());
        if (null != exist && exist >= 1) {
            teamRepository.update(team);
        } else {
            teamRepository.insert(team);
        }
    }

    public void update(Team team) {
        teamRepository.update(team);
    }
}
