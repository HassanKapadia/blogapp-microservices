package com.hancy.gateway.filter;

import com.hancy.blogapp.common.constants.BlogAppHeaderConstants;
import com.hancy.blogapp.common.security.JwtUtil;
import com.hancy.gateway.config.GatewayWhitelistConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

  private final JwtUtil jwtUtil;
  private final GatewayWhitelistConfig whitelistConfig;

  @Autowired
  public JwtAuthenticationFilter(JwtUtil jwtUtil, GatewayWhitelistConfig whitelistConfig) {
    this.jwtUtil = jwtUtil;
    this.whitelistConfig = whitelistConfig;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    String path = exchange.getRequest().getURI().getPath();

    // Skip the authentication if path is white-listed
    if (whitelistConfig.isWhitelisted(path)) {
      return chain.filter(exchange);
    }

    // Extract Authorization header
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    String token = authHeader.substring(7);

    // Validate token
    if (!jwtUtil.validateToken(token)) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    // Extract claims
    Long userId = jwtUtil.extractUserId(token);
    String email = jwtUtil.extractEmail(token);

    // Inject headers
    ServerWebExchange mutatedExchange =
        exchange
            .mutate()
            .request(
                r ->
                    r.header(BlogAppHeaderConstants.AUTH_USER_ID, String.valueOf(userId))
                        .header(BlogAppHeaderConstants.AUTH_USER_EMAIL, email))
            .build();

    return chain.filter(mutatedExchange);
  }
}
