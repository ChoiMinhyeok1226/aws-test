package com.multi.restproduct.auth.service;

import com.multi.restproduct.common.exception.DuplicateUsernameException;
import com.multi.restproduct.common.jwt.dto.TokenDto;
import com.multi.restproduct.common.jwt.service.TokenService;
import com.multi.restproduct.member.model.dao.MemberMapper;
import com.multi.restproduct.member.model.dto.MemberReqDto;
import com.multi.restproduct.member.model.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final CustomUserDetailService customUserDetailService;
    private final TokenService tokenService;

    @Transactional
    public MemberResDto signup(MemberReqDto memberReqDto) {
        if (memberMapper.findByEmail(memberReqDto.getMemberEmail()).isPresent()) {
            throw new DuplicateUsernameException("이메일이 중복됩니다.");
        }

        memberReqDto.setMemberPassword(passwordEncoder.encode(memberReqDto.getMemberPassword()));

        int result = memberMapper.insertMember(memberReqDto);
        if (result <= 0) {
            throw new RuntimeException("회원가입에 실패했습니다.");
        }
        return MemberResDto.builder()
                .memberCode(memberReqDto.getMemberCode())
                .memberId(memberReqDto.getMemberId())
                .memberName(memberReqDto.getMemberName())
                .memberEmail(memberReqDto.getMemberEmail())
                .memberRole(memberReqDto.getMemberRole())
                .build();
    }

    public TokenDto login(MemberReqDto memberReqDto) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(memberReqDto.getMemberEmail());
        if(!passwordEncoder.matches(memberReqDto.getMemberPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }


        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        Map<String, Object> loginData = new HashMap<>();
        loginData.put("email", memberReqDto.getMemberEmail());
        loginData.put("roles", roles);

        return tokenService.createToken(loginData);
    }
}
