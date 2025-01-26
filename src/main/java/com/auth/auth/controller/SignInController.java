package com.auth.auth.controller;

import com.auth.auth.controller.dto.SignInByEmailDTO;
import com.auth.auth.controller.dto.SignInByIdDTO;
import com.auth.auth.exception.MissingSignInParametersException;
import com.auth.auth.service.AuthService;
import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import com.auth.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {
  private final TokenService tokenService;
  private final AuthService authService;
  private final HeaderService headerService;

  @PostMapping("/id")
  public ResponseEntity<String> signin(@RequestBody SignInByIdDTO signInByIdDTO, ServerWebExchange exchange) throws MissingSignInParametersException, UserNotFoundException {
    ServerHttpResponse response = exchange.getResponse();
    authService.signIn(signInByIdDTO);
    headerService.addHeader(tokenService.createToken(signInByIdDTO.id()), response);
    return ResponseEntity.ok("User id "+ signInByIdDTO.id() + " successfully signed in.");
  }

  @PostMapping("/email")
  public ResponseEntity<String> signin(@RequestBody SignInByEmailDTO signInByEmailDTO, ServerWebExchange exchange) throws MissingSignInParametersException, UserNotFoundException {
    ServerHttpResponse response = exchange.getResponse();
    authService.signIn(signInByEmailDTO);
    headerService.addHeader(tokenService.createToken(signInByEmailDTO.email()), response);
    return ResponseEntity.ok("User id "+ signInByEmailDTO.email() + " successfully signed in.");
  }
}
