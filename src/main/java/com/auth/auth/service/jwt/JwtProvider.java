package com.auth.auth.service.jwt;

import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;

public class JwtProvider {
  public String createToken(String userId, Long expiration, Key key) {
    Claims claims = Jwts.claims().setSubject(userId);
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration * 1000);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  };

  public String getUserId(String token, Key key) {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    } catch(Exception e) {
      return null;
    }
  };

  public Boolean validateToken(String token, Key key) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  };
}
