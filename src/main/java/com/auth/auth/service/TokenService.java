package com.auth.auth.service;

import com.auth.auth.exception.AccessTokenAndRefreshTokenNotMatchesException;
import com.auth.auth.service.jwt.implementation.JwtAccessTokenProvider;
import com.auth.auth.service.jwt.implementation.JwtRefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final JwtAccessTokenProvider jwtAccessTokenProvider;
  private final JwtRefreshTokenProvider jwtRefreshTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;

  public Pair<String, String> createToken(String userId) {
    String accessToken = jwtAccessTokenProvider.createToken(userId);
    String refreshToken = jwtRefreshTokenProvider.createToken(userId);
    redisTemplate.opsForValue().set(userId.toString(), refreshToken);
    return Pair.of(accessToken, refreshToken);
  }

  public Boolean deleteToken(String refreshToken) {
    return redisTemplate.delete(jwtRefreshTokenProvider.getUserId(refreshToken));
  }

  public Boolean validateAccessToken(String accessToken) {
    String token = accessToken.split(" ")[1];
    if (jwtAccessTokenProvider.validateToken(token)) {
      return true;
    }
    return false;
  }

  public Boolean validateRefreshToken(String refreshToken) {
    try {
      String userId = jwtRefreshTokenProvider.getUserId(refreshToken);
      String redis = redisTemplate.opsForValue().get(userId.toString());
      return jwtRefreshTokenProvider.validateToken(refreshToken) && redis != null && redis.equals(refreshToken);
    } catch (Exception e) {
      return false;
    }
  }

  public Pair<String, String> refresh(String refreshToken) {
    String userId = jwtRefreshTokenProvider.getUserId(refreshToken);
    deleteToken(refreshToken);
    Pair<String, String> token = createToken(userId);
    return token;
  }

  public String getUserId(String accessToken) {
    String token = accessToken.split(" ")[1];
    return jwtAccessTokenProvider.getUserId(token);
  }


}
