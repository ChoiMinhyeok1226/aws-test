package com.multi.restproduct.member.model.dao;


import com.multi.restproduct.member.model.dto.MemberDto;
import com.multi.restproduct.member.model.dto.MemberReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    Optional<MemberDto> findByMemberId(String memberId);

    Optional<MemberDto> findByEmail(String email);

    int insertMember(MemberReqDto memberReqDto);
}
