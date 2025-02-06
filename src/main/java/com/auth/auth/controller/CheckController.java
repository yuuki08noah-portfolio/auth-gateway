package com.auth.auth.controller;

import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import com.auth.gateway.exception.NotUserException;
import com.auth.global.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CheckController {
  private final TokenService tokenService;
  private final HeaderService headerService;

  @GetMapping("/check")
  public ResponseEntity<BaseResponse> check(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    String accessToken = request.getHeaders().getFirst("Authorization");
    String refreshToken = request.getHeaders().getFirst("RefreshToken");
    if (accessToken == null && refreshToken == null) {
      return ResponseEntity.ok(new BaseResponse(true, "Not signed", Map.of("signed", false)));
    }
    if (!accessToken.equals(null) && tokenService.validateAccessToken(accessToken)) {
      return ResponseEntity.ok(new BaseResponse(true, "Signed", Map.of("signed", true)));
    } else if(!refreshToken.equals(null) && tokenService.validateRefreshToken(refreshToken)) {
      headerService.addHeader(tokenService.refresh(refreshToken), response);
      return ResponseEntity.ok(new BaseResponse(true, "Signed", Map.of("signed", true)));
    }
    return ResponseEntity.ok(new BaseResponse(true, "Not signed", Map.of("signed", false)));
  }
}
