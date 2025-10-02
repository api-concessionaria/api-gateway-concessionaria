package br.ufpb.dcx.apigatewayconcessionaria.config;

import br.ufpb.dcx.apigatewayconcessionaria.security.SecurityConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (request.getURI().getPath().contains("/api/auth")) {
            return chain.filter(exchange);
        }

        String header = request.getHeaders().getFirst(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build()
                    .verify(token);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }
}