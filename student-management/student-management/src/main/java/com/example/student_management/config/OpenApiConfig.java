package com.example.student_management.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .version("1.0.0")
                        .description("API for managing students with create, update, delete, search, pagination and sorting functionality.")
                        .contact(new Contact()
                                .name("Hashini Hewa Witharana")
                                .email("hashinihewawitharana24@gmail.com")
                                 )

                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local server")
                ));

    }
}
