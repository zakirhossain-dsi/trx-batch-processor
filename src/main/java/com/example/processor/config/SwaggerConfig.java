package com.example.processor.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("trx-batch-processor")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Transaction Batch Processor")
                        .version("1.0")
                        .description("API documentation for the Transaction Batch Processor")
                        .contact(new Contact().name("Your Name").email("your.email@example.com"))
                );
    }
}
