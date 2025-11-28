package com.multi.restproduct.common.jwt.dao;

import com.multi.restproduct.common.jwt.dto.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {


    Optional<RefreshToken> findByEmail(String email);

    void deleteRefreshTokenByEmail(String email);

    void insertRefreshTokenByEmail(RefreshToken newToken);
}
