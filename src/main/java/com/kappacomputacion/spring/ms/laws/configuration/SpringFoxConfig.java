package com.kappacomputacion.spring.ms.laws.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ikc-spring-laws-public")
                .pathsToMatch("/v1/ikc-spring-laws/**")
                .build();
    }
}
