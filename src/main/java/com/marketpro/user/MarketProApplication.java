package com.marketpro.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MarketProApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketProApplication.class, args);
    }

}
