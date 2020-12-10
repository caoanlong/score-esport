package com.dragon.scoreapi.service;

import com.dragon.scoreapi.model.CsgoTeamStats;
import com.dragon.scoreapi.model.DotaTeamStats;
import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> findList(String time, List<String> gameTypes) {
        return matchRepository.findList(time, gameTypes);
    }

    @Transactional
    public void save(Match match) {
        Integer exist = matchRepository.isExist(match.getMatchId());
        if (null != exist && exist >= 1) {
            matchRepository.update(match);
        } else {
            matchRepository.insert(match);
        }
    }
}
