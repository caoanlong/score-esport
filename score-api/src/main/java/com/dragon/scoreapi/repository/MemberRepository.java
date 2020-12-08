package com.dragon.scoreapi.repository;

import com.dragon.scoreapi.model.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    List<Member> findAll();
    Member findById(Integer id);
    Member findByPhone(String phone);

    void insert(Member member);
    void update(Member member);
}
