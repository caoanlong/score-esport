package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.TeamTournament;
import com.dragon.scoreapi.model.Tournament;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository {
    List<Tournament> findAll();
    List<Tournament> findList(@Param("gameType") String gameType, @Param("status") Integer status);
    Tournament findById(String tournamentId);
    Integer isExist(String tournamentId);
    void insert(Tournament tournament);
    void update(Tournament tournament);
    Integer isTeamTournamentExist(TeamTournament teamTournament);
    void insertTeamTournament(TeamTournament teamTournament);
}
