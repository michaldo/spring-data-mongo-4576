package com.example.springmongoversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringMongoVersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoVersionApplication.class, args);
    }

    @Bean
    MongoClientSettingsBuilderCustomizer shortMongoTimeout() {
        return clientSettingsBuilder -> clientSettingsBuilder.applyToClusterSettings(
                clusterSettings -> clusterSettings.serverSelectionTimeout(6, TimeUnit.SECONDS));
    }

}
