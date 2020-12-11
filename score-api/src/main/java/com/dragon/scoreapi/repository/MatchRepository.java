package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Match;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository {
    List<Match> findList(@Param("date") Date date, @Param("gameTypes") String[] gameTypes, @Param("gameStatus") Integer gameStatus);
    Integer isExist(Integer matchId);
    void insert(Match match);
    void update(Match match);
}
