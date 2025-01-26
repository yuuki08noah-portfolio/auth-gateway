package com.auth.gateway.filter;

import com.auth.gateway.exception.NotAnonymousException;
import com.auth.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnonymousFilter extends AbstractGatewayFilterFactory<Object> {

  private final TokenService tokenService;

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String accessToken = request.getHeaders().getFirst("Authorization");
      String refreshToken = request.getHeaders().getFirst("RefreshToken");

      System.out.println("accessToken: " + accessToken);
      System.out.println("refreshToken: " + refreshToken);
      log.debug("AccessToken: {}", accessToken);
      log.debug("RefreshToken: {}", refreshToken);

      if (accessToken == null && refreshToken == null) {
        return chain.filter(exchange);
      }

      if (accessToken != null && tokenService.validateAccessToken(accessToken)) {
        log.error("Access Token is valid, only anonymous users can access.");
        return Mono.error(new NotAnonymousException("Anonymous only can reach"));
      }

      if (refreshToken != null && tokenService.validateRefreshToken(refreshToken)) {
        log.error("Refresh Token is valid, only anonymous users can access.");
        return Mono.error(new NotAnonymousException("Anonymous only can reach"));
      }

      return chain.filter(exchange);
    };
  }
}
