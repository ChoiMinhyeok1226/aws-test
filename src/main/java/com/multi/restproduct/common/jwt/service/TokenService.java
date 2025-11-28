package com.multi.restproduct.common.jwt.service;

import com.multi.restproduct.common.exception.RefreshTokenException;
import com.multi.restproduct.common.jwt.TokenProvider;
import com.multi.restproduct.common.jwt.dao.RefreshTokenMapper;
import com.multi.restproduct.common.jwt.dto.RefreshToken;
import com.multi.restproduct.common.jwt.dto.TokenDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenMapper refreshTokenMapper;

    @Transactional(noRollbackFor = RefreshTokenException.class)
    public <T> TokenDto createToken(T t) {

        String memberEmail;
        List<String> roles;
        String accessToken;
        String refreshToken;

        if (t instanceof Map) {

            Map<String, Object> data = (Map<String, Object>) t;
            memberEmail = (String) data.get("email");
            roles = (List<String>) data.get("roles");

            log.info("Map MemberEmail >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", memberEmail);
            log.info("Map Roles >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", roles);
        } else if (t instanceof String) {
            String jwt = resolveToken((String) t);
            Claims claims = tokenProvider.parseClaims(jwt);

            memberEmail = claims.getSubject();
            String role = (String) claims.get("auth");
            roles = Arrays.asList(role.split(","));

            log.info("Map MemberEmail >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", memberEmail);
            log.info("Map Roles >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", roles);
        }
        else {
            throw new IllegalArgumentException("Invalid token type !!");
        }

        refreshToken = handleRefreshToken(memberEmail);
        accessToken = createAccessToken(memberEmail, roles);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String resolveToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    private String handleRefreshToken(String memberEmail) {
        Optional<RefreshToken> existingToken = refreshTokenMapper.findByEmail(memberEmail);
        if (existingToken.isPresent()) {//refresh token이 존재할 경우
            RefreshToken refreshToken = existingToken.get();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredDate = refreshToken.getExpiredAt();

            if (expiredDate.isBefore(now)) {
                refreshTokenMapper.deleteRefreshTokenByEmail(memberEmail);
                throw new RefreshTokenException("Refresh Token이 만료되었습니다, 다시 로그인 해주세요");
            } else {
                return refreshToken.getRefreshToken();
            }
        } else {
            String reToken = createRefreshToken(memberEmail);

            if (tokenProvider.validateToken(reToken)) {
                RefreshToken newToken = RefreshToken.builder()
                        .email(memberEmail)
                        .refreshToken(reToken)
                        .expiredAt(tokenProvider.getRefreshTokenExpiry())
                        .issuedAt(LocalDateTime.now())
                        .build();

                refreshTokenMapper.insertRefreshTokenByEmail(newToken);

            }
            return reToken;
        }

    }


    private String createAccessToken(String memberEmail, List<String> roles) {
        return tokenProvider.generateToken(memberEmail, roles, "A");
    }

    private String createRefreshToken(String memberEmail) {
        return tokenProvider.generateToken(memberEmail, null, "R");
    }

    public void deleteRefreshToken(String accessToken) {
        String token = resolveToken(accessToken);
        String email = tokenProvider.getUserId(token);
        refreshTokenMapper.deleteRefreshTokenByEmail(email);
        log.info("Refresh Token 삭제 완료: 사용자 email {}", email);
    }
}

