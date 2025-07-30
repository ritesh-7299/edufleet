package com.ritesh.edufleet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI edufleetOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Edufleet API")
                                .version("1.0.0")
                                .description("Api documentation for edufleet system")
                );
    }
}
