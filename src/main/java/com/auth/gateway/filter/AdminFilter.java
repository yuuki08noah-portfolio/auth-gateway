package com.auth.gateway.filter;

import com.auth.auth.exception.AccessTokenAndRefreshTokenNotMatchesException;
import com.auth.gateway.exception.AccessTokenInvalidException;
import com.auth.gateway.exception.NotAdminException;
import com.auth.auth.service.AuthService;
import com.auth.auth.service.TokenService;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.repository.entity.Role;
import com.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AdminFilter extends AbstractGatewayFilterFactory<Object> {
  private final AuthService authService;
  private final TokenService tokenService;
  private final UserService userService;

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      ServerHttpResponse response = exchange.getResponse();
      String accessToken = response.getHeaders().getFirst("Authorization");
      if (accessToken == null) {
        return Mono.error(new NotAdminException("Admin only can reach"));
      }
      try {
        String userId = tokenService.getUserId(accessToken);
        Role role = userService.getUserById(userId).role();
        if (role != Role.ADMIN) {
          return Mono.error(new NotAdminException("Admin only can reach"));
        }
        return chain.filter(exchange);
      } catch (UserNotFoundException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
