package com.auth.auth.controller;

import com.auth.auth.exception.InvalidJwtException;
import com.auth.auth.service.AuthService;
import com.auth.auth.service.TokenService;
import com.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;

@Controller
@RequiredArgsConstructor
public class SignOutController {
  private final TokenService tokenService;

  @GetMapping("/signout")
  public ResponseEntity<String> signout(ServerWebExchange exchange) throws InvalidJwtException {
    ServerHttpRequest request = exchange.getRequest();
    String refreshToken = request.getHeaders().getFirst("RefreshToken");
    if (refreshToken == null) {
      throw new InvalidJwtException("Invalid refresh token.");
    }
    System.out.println(refreshToken);
    tokenService.deleteToken(request.getHeaders().getFirst("RefreshToken"));
    return ResponseEntity.ok("Signed out successfully.");
  }
}
