package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Player;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository {
    List<Player> findAll();
    List<Player> findList(@Param("gameType") String gameType);
    Player findById(String id);
    Integer isExist(String playerId);
    void insert(Player player);
    void update(Player player);
}
