package br.ufpb.dcx.apigatewayconcessionaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cliente-service-route", r -> r.path("/api/clientes/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://cliente-service"))

                .route("funcionario-service-route", r -> r.path("/api/funcionarios/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://funcionario-service"))

                .route("veiculo-service-route", r -> r.path("/api/veiculos/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://veiculo-service"))

                .route("venda-service-route", r -> r.path("/api/vendas/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://venda-service"))

                .route("auth-service-route", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .build();
    }
}
