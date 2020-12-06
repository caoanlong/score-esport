package com.dragon.score.service.impl;

import com.dragon.score.model.Member;
import com.dragon.score.repository.MemberRepository;
import com.dragon.score.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
