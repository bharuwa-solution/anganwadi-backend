package com.anganwadi.anganwadi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";


    @Bean
    public Docket swaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.anganwadi"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Anganwadi App API",
                "List Of APi's Of Project",
                "1.0",
                "Token Based Auth",
                new springfox.documentation.service.Contact("BrawaSolutions", "https://www.bharuwasolutions.com/", "info@bharuwasolutions.com"),
                "Token Based",
                "https://www.bharuwasolutions.com/",
                Collections.emptyList());
    }


}
