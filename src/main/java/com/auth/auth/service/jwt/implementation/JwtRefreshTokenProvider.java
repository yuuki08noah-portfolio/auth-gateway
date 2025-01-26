package com.auth.auth.service.jwt.implementation;

import com.auth.auth.service.jwt.JwtProvider;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtRefreshTokenProvider extends JwtProvider {
  private static final String refreshTokenKey;
  private static final Long expiration;
  private final Key refreshKey;

  static {
    refreshTokenKey = System.getenv("JWT_REFRESHTOKEN_KEY");
    expiration = Long.parseLong(System.getenv("JWT_REFRESHTOKEN_EXPIRATION"));
  }

  public JwtRefreshTokenProvider() {
    refreshKey = Keys.hmacShaKeyFor(refreshTokenKey.getBytes());
  }

  public String createToken(String userId) {
    return super.createToken(userId, expiration, refreshKey);
  }

  public Boolean validateToken(String token) {
    return super.validateToken(token, refreshKey);
  }

  public String getUserId(String token) {
    return super.getUserId(token, refreshKey);
  }
}