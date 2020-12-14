package com.dragon.scoreapi.service;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.TeamTournament;
import com.dragon.scoreapi.model.Tournament;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public List<Tournament> findList(String gameType, Integer status) {
        return tournamentRepository.findList(gameType, status);
    }

    public Tournament findById(String tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId);
        if (null == tournament) throw new CommonException(ResCode.DATA_NOT_FOUND);
        return tournament;
    }

    @Transactional
    public void save(Tournament tournament) {
        Integer exist = tournamentRepository.isExist(tournament.getTournamentId());
        if (null != exist && exist >= 1) {
            tournamentRepository.update(tournament);
        } else {
            tournamentRepository.insert(tournament);
        }
    }

    public void update(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    public void insertTeamTournament(TeamTournament teamTournament) {
        Integer exist = tournamentRepository.isTeamTournamentExist(teamTournament);
        if (null == exist || exist < 1) {
            tournamentRepository.insertTeamTournament(teamTournament);
        }
    }
}
