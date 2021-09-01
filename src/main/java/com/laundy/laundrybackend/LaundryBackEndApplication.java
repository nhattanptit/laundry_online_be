package com.laundy.laundrybackend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LaundryBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaundryBackEndApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My App").version("1.0.0"))
                // Components section defines Security Scheme "mySecretHeader"
                .components(new Components()
                        .addSecuritySchemes("mySecretHeader", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                // AddSecurityItem section applies created scheme globally
                .addSecurityItem(new SecurityRequirement().addList("mySecretHeader"));
    }

}
