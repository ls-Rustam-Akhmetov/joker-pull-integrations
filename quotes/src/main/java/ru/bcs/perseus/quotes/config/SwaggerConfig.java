package ru.bcs.perseus.quotes.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // show controllers with @Api only
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket swaggerPartnerApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-1.0")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // show controllers with @Api only
                .paths(regex("/api/v1.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1").title("Partner quotes API").build());
    }
}
