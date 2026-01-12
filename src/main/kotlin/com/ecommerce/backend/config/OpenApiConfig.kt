package com.ecommerce.backend.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {

        val scheme = "BearerAuth"

        return OpenAPI()
            .info(
                Info()
                    .title("E-Commerce Backend API")
                    .description("Swagger documentation for E-Commerce Backend")
                    .version("1.0.0")
            )
            .addSecurityItem(SecurityRequirement().addList(scheme))
            .components(
                io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes(
                        scheme,
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
