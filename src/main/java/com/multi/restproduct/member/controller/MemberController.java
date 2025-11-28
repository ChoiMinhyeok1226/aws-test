package com.multi.restproduct.member.controller;

import com.multi.restproduct.common.ResponseDto;
import com.multi.restproduct.member.model.dto.MemberDto;
import com.multi.restproduct.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{memberid}")
    public ResponseEntity<ResponseDto> findByMemberId(@PathVariable("memberid") String memberId) {
        Optional<MemberDto> member = memberService.findByMemberId(memberId);
        if (member.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "회원정보를 찾을 수 없습니다.", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "회원 조회 성공.", member));
    }

    @GetMapping("/members/{email}")
    public ResponseEntity<ResponseDto> findByEmail(@PathVariable("email") String email) {
        Optional<MemberDto> byEmail = memberService.findByEmail(email);

        if (byEmail.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "회원정보를 찾을 수 없습니다.", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "회원 조회 성공.", byEmail));
    }
}
