package com.okta.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, TokenRelayGatewayFilterFactory tokenRelay) {
        return builder.routes()
                .route("servlet-app", r -> r.path("/profile", "/")
                        .filters(f -> f.filter(tokenRelay.apply()))
                        .uri("http://localhost:8000"))
                .build();
    }
}
