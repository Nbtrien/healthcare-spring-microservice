package com.healthcare.backend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Keycloak", openIdConnectUrl = "http://localhost:8181/realms/spring-realm/" +
        ".well-known/openid-configuration", scheme = "bearer", type = SecuritySchemeType.OPENIDCONNECT, in =
        SecuritySchemeIn.HEADER)
public class DoctorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoctorServiceApplication.class, args);
    }
}
