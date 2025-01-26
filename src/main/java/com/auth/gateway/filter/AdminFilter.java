package com.auth.gateway.filter;

import com.auth.auth.exception.AccessTokenAndRefreshTokenNotMatchesException;
import com.auth.gateway.exception.NotAdminException;
import com.auth.auth.service.AuthService;
import com.auth.auth.service.TokenService;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.repository.entity.Role;
import com.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
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
      String refreshToken = response.getHeaders().getFirst("RefreshToken");
      try {
        String userId = tokenService.getUserId(accessToken, refreshToken);
        Role role = userService.getUserById(userId).role();
        if (role != Role.ADMIN) {
          return Mono.error(new NotAdminException("Admin only can reach"));
        }
      } catch (AccessTokenAndRefreshTokenNotMatchesException e) {
        return Mono.error(new AccessTokenAndRefreshTokenNotMatchesException("Access Token or Refresh Token Not Matches"));
      } catch (UserNotFoundException e) {
        return Mono.error(new UserNotFoundException("User Not Found"));
      }
      return chain.filter(exchange);
    };
  }
}
