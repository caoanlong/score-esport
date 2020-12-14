package com.dragon.scoreapi.service;

import com.dragon.scoreapi.enums.ResCode;
import com.dragon.scoreapi.model.Member;
import com.dragon.scoreapi.model.exception.CommonException;
import com.dragon.scoreapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Integer id) {
        Member member = memberRepository.findById(id);
        if (null == member) throw new CommonException(ResCode.DATA_NOT_FOUND);
        member.setPassword(null);
        return member;
    }
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }

    public void insert(Member member) {
        memberRepository.insert(member);
    }
    public void update(Member member) {
        memberRepository.update(member);
    }
}
