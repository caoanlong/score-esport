package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Match;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository {
    List<Match> findList(String time, List<String> gameTypes);
    Integer isExist(String matchId);
    void insert(Match match);
    void update(Match match);
//    void insertCsgoTeamStats(List<CsgoTeamStats> csgoTeamStatsList);
//    void insertDotaTeamStats(List<DotaTeamStats> dotaTeamStatsList);
}
