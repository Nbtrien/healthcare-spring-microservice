package com.healthcare.backend.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("doctor-service", r -> r.path("/api/doctors/**").uri("lb://doctor-service"))
                .route("patient-service", r -> r.path("/api/patients/**").uri("lb://patient-service"))
                .route("patient-service-docs", r -> r
                        .path("/patient-service/v3/api-docs")
                        .uri("lb://patient-service"))
                .route("doctor-service-docs", r -> r
                        .path("/doctor-service/v3/api-docs")
                        .uri("lb://doctor-service"))
                .build();
    }

}