package com.auth.auth.controller;

import com.auth.auth.exception.InvalidJwtException;
import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequiredArgsConstructor
public class RefreshController {
  private final TokenService tokenService;
  private final HeaderService headerService;

  @GetMapping("/refresh")
  public ResponseEntity refresh(ServerWebExchange exchange) throws InvalidJwtException {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    String refreshToken = request.getHeaders().getFirst("RefreshToken");

    if (refreshToken == null || !tokenService.validateRefreshToken(refreshToken)) {
      throw new InvalidJwtException("Invalid refresh token.");
    }
    tokenService.refresh(refreshToken);
    headerService.addHeader(tokenService.refresh(refreshToken), response);
    return ResponseEntity.ok("Refreshed successfully.");
  }
}
