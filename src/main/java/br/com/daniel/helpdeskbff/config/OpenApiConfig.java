package br.com.daniel.helpdeskbff.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${springdoc.openapi.title}")
    private String title;

    @Value("${springdoc.openapi.description}")
    private String description;

    @Value("${springdoc.openapi.version}")
    private String version;

    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
