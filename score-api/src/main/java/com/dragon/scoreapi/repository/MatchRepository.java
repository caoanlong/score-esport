package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Match;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository {
    List<Match> findList();
}
