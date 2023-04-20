package com.gyeol.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@ComponentScan
@EnableJpaAuditing
@EnableAsync
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize(){
        return pageableResolver -> {
            pageableResolver.setOneIndexedParameters(true);
        };
    }
}
