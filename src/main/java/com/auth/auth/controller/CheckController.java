package com.auth.auth.controller;

import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import com.auth.gateway.exception.NotUserException;
import com.auth.global.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    String accessToken = request.getHeaders().getFirst("Authorization");
    if (accessToken == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(true, "Not signed", Map.of("signed", false)));
    }
    if (tokenService.validateAccessToken(accessToken)) {
      return ResponseEntity.ok(new BaseResponse(true, "Signed", Map.of("signed", true)));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(true, "Not signed", Map.of("signed", false)));
  }
}
