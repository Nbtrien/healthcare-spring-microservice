package com.healthcare.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.pathMatchers("/swagger-ui/**",
                                                                                                  "/v3/api-docs/**",
                                                                                                  "/patient-service" + "/swagger" + "-ui.html", "/swagger-ui.html", "/webjars/swagger" + "-ui/**", "/patient-service" + "/v3/api-docs/**")
                        .permitAll().anyExchange().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())).build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.applyPermitDefaultValues();
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        configuration.setMaxAge(3600L); // Cache preflight response for 1 hour
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest()
//                        .authenticated())
//                .oauth2ResourceServer(t -> t.jwt(Customizer.withDefaults()))
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//        return http.build();
//    }

//    @Bean
//    public DefaultMethodSecurityExpressionHandler securityExpressionHandler() {
//        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
//                new DefaultMethodSecurityExpressionHandler();
//        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
//        return defaultMethodSecurityExpressionHandler;
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter c = new JwtAuthenticationConverter();
//        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
//        cv.setAuthorityPrefix("");
//        cv.setAuthoritiesClaimName("post_roles");
//        c.setJwtGrantedAuthoritiesConverter(cv);
//        return c;
//    }
}
