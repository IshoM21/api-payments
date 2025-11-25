package com.app.payments.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Payments API",
        version = "v1",
        description = "API para registrar clientes, compras y pagos parciales.",
        contact = @Contact(
            name = "Payments App",
            email = "soporte@example.com"
        ),
        license = @License(
            name = "MIT",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class OpenApiConfig {

    @Bean
    GroupedOpenApi paymentsApi() {
        return GroupedOpenApi.builder()
                .group("payments-api")
                .pathsToMatch("/api/**")   // documenta solo tus endpoints de negocio
                .build();
    }

}
