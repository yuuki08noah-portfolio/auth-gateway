package com.auth.auth.service;

import org.springframework.data.util.Pair;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class HeaderService {
  public void addHeader(Pair<String, String> token, ServerHttpResponse response) {
    System.out.println(token.getFirst());
    response.getHeaders().set("Authorization", "Bearer " + token.getFirst());
    response.getHeaders().set("RefreshToken", token.getSecond());
  }
}
