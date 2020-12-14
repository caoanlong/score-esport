package com.dragon.scoreapi.service;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Player;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }
    public List<Player> findList(String gameType) {
        return playerRepository.findList(gameType);
    }

    public Player findById(String id) {
        Player player = playerRepository.findById(id);
        if (null == player) throw new CommonException(ResCode.DATA_NOT_FOUND);
        return player;
    }

    @Transactional
    public void save(Player player) {
        Integer exist = playerRepository.isExist(player.getId());
        if (null != exist && exist >= 1) {
            playerRepository.update(player);
        } else {
            playerRepository.insert(player);
        }
    }

    public void update(Player player) {
        playerRepository.update(player);
    }
}
