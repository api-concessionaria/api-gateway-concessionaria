package br.ufpb.dcx.apigatewayconcessionaria.filters;

import br.ufpb.dcx.apigatewayconcessionaria.security.SecurityConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        String header = request.getHeaders().getFirst(SecurityConstants.HEADER_STRING);

        if (isAuthPath(path)) {
            return chain.filter(exchange);
        }

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");
            JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isAuthPath(String path) {
        return path.startsWith("/api/auth") ||
                path.contains("/auth/register") ||
                path.contains("/auth/login");
    }

}

