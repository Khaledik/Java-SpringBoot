package fr.diginamic.hello.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API REST VILLES & DÉPARTEMENT DE FRANCE")
                .version("1.0")
                .description("Cette api rest permets d'obtenir des villes et des département se situant en france.")
                .contact(new Contact()
                        .name("Khaled")
                ));
    }

}
