package com.ely.secretify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import reactor.Environment;
import reactor.bus.EventBus;
@SpringBootApplication
@Service
public class SecretifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretifyApplication.class, args);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

}
