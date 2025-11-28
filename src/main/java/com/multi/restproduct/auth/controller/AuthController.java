package com.multi.restproduct.auth.controller;

import com.multi.restproduct.auth.service.AuthService;
import com.multi.restproduct.common.ResponseDto;
import com.multi.restproduct.common.jwt.dto.TokenDto;
import com.multi.restproduct.common.jwt.service.TokenService;
import com.multi.restproduct.member.model.dto.MemberReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    //가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody MemberReqDto memberReqDto) {

        return ResponseEntity.ok(new ResponseDto(HttpStatus.CREATED, "회원가입 성공", authService.signup(memberReqDto)));

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody MemberReqDto memberReqDto) {

        TokenDto token = authService.login(memberReqDto);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "로그인 성공", token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh(@RequestHeader("Authorization") String accessToken) {
        TokenDto token = tokenService.createToken(accessToken);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "로그인 성공", token));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
        tokenService.deleteRefreshToken(accessToken);

        return ResponseEntity.ok("로그아웃 성공 및 Refresh Token 제거 완료");
    }

}




