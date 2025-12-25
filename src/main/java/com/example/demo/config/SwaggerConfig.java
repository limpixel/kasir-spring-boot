package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple Inventory Management API")
                        .description("A simple Spring Boot application for managing products and transactions with full CRUD operations")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Demo Developer")
                                .email("demo@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ))
                .tags(List.of(
                        new Tag()
                                .name("Product Management")
                                .description("APIs for managing products in the inventory"),
                        new Tag()
                                .name("Transaction Management")
                                .description("APIs for managing sales and purchase transactions")
                ));
    }
}