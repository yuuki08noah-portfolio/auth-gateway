package com.auth.gateway.filter;

import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import com.auth.gateway.exception.NotUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserFilter extends AbstractGatewayFilterFactory<Object> {
  private final TokenService tokenService;
  private final HeaderService headerService;

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();
      String accessToken = request.getHeaders().getFirst("Authorization");
      String refreshToken = request.getHeaders().getFirst("RefreshToken");
      if (accessToken == null || refreshToken == null) {
        return Mono.error(new NotUserException("User only can reach"));
      }
      if (tokenService.validateAccessToken(accessToken)) {
        return chain.filter(exchange);
      } else {
        if (tokenService.validateRefreshToken(refreshToken)) {
          headerService.addHeader(tokenService.refresh(refreshToken), response);
          return chain.filter(exchange);
        };
      }
      return Mono.error(new NotUserException("User only can reach"));
    };
  }
}
