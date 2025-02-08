package com.auth.auth.controller;

import com.auth.auth.service.TokenService;
import com.auth.global.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserIdController {
  private final TokenService tokenService;

  @GetMapping("/userId")
  public ResponseEntity<BaseResponse> getUserId(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String accessToken = request.getHeaders().getFirst("Authorization").split("Bearer ")[1];
    return ResponseEntity.ok(new BaseResponse(true, "Successfully got userId",  Map.of("userId", tokenService.getUserId(accessToken))));
  }
}
