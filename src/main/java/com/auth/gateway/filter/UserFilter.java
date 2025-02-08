package com.auth.gateway.filter;

import com.auth.auth.service.HeaderService;
import com.auth.auth.service.TokenService;
import com.auth.gateway.exception.AccessTokenInvalidException;
import com.auth.gateway.exception.NotUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
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
      String accessToken = request.getHeaders().getFirst("Authorization");
      if (accessToken == null) {
        return Mono.error(new AccessTokenInvalidException("AccessToken is null."));
      }
      if (tokenService.validateAccessToken(accessToken)) {
        return chain.filter(exchange);
      }
      return Mono.defer(() -> {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.error(new AccessTokenInvalidException("AccessToken is invalid."));
      });
    };
  }
}
