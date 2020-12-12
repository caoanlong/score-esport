package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository {
    List<Team> findList(@Param("gameType") String gameType);
    Integer isExist(String teamId);
    void insert(Team team);
    void update(Team team);
}
