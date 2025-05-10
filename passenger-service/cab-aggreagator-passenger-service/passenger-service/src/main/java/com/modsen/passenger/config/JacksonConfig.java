package com.modsen.passenger.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.modsen.passenger.config.serialization.PageJsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class JacksonConfig {

    @Bean
    public Module pageModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Page.class, new PageJsonDeserializer());
        return module;
    }
}