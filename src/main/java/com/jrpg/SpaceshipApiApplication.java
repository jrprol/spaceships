package com.jrpg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.jrpg")
@EntityScan(basePackages = "com.jrpg.model")
@EnableJpaRepositories(basePackages = "com.jrpg.repository")
public class SpaceshipApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpaceshipApiApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner runner(CacheManager cacheManager) {
        return args -> {
            System.out.println("Cache manager: " + cacheManager);
        };
    }
    
}
