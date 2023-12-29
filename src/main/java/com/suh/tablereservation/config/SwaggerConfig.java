package com.suh.tablereservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI tableReservationOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Table Reservation API")
                        .description("SUH SAECHAN")
                        .version("0.1")
                );
    }
}
