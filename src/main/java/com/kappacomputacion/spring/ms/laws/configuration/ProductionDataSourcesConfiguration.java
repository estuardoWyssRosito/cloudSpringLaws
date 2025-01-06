/*
* When you use @Profile annotations on your configuration classes or properties,
* Spring will select the appropriate beans based on the active profile.
* */

package com.kappacomputacion.spring.ms.laws.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@Configuration
@Profile("prod")
public class ProductionDataSourcesConfiguration {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/laws")
                .username("root")
                .password("Hoperos#4533")
                .build();
    }
}
