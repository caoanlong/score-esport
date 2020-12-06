package com.dragon.score.repository;

import com.dragon.score.model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    List<Member> findAll();
}
