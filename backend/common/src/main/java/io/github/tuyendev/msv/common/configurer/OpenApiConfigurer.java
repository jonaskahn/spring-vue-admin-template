package io.github.tuyendev.msv.common.configurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfigurer {
    @Bean
    public OpenAPI defaultSwaggerConfig() {
        return new OpenAPI()
                .info(new Info().title("Mee Spring Vue API")
                        .description("Demo")
                        .version("v0.0.1")
                        .license(new License().name("MIT").url("http://tuyendev.github.io")));
    }
}
