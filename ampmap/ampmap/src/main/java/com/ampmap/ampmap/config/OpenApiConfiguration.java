package com.ampmap.ampmap.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AMPMap",
                version = "0.0.1"
        )
)
public class OpenApiConfiguration {
}
