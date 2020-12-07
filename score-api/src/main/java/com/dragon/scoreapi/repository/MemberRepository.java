package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    List<Member> findAll();
}