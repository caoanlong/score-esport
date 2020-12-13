package com.dragon.scoreapi.service;

import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public List<Match> findList(Date date, String[] gameTypes, Integer gameStatus) {

        return matchRepository.findList(date, gameTypes, gameStatus);
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
