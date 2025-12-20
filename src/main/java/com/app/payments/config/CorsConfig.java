package com.app.payments.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos (local development)
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://*.vercel.app"
        ));
//        config.setAllowedOrigins(List.of("*"));

        // Métodos permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Headers permitidos
//        config.setAllowedHeaders(List.of("*"));
        // Mejor explícitos para seguridad:
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));

        // Permitir credenciales si luego usarás cookies / auth
        config.setAllowCredentials(false);

        // Tiempo de cache de preflight requests
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
