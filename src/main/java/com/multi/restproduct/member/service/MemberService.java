package com.multi.restproduct.member.service;

import com.multi.restproduct.member.model.dao.MemberMapper;
import com.multi.restproduct.member.model.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    public final MemberMapper memberMapper;


    public Optional<MemberDto> findByMemberId(String memberId) {
        return memberMapper.findByMemberId(memberId);
    }

    public Optional<MemberDto> findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }
}
