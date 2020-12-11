package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Tournament;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository {
    List<Tournament> findList(@Param("gameType") String gameType, @Param("status") Integer status);
    Integer isExist(Integer tournamentId);
    void insert(Tournament tournament);
    void update(Tournament tournament);
}
