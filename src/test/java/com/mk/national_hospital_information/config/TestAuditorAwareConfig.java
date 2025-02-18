package com.mk.national_hospital_information.config;

import java.util.Optional;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

@TestConfiguration
public class TestAuditorAwareConfig {

    @Bean
    public AuditorAware<String> auditorAwareImpl() {
        return () -> Optional.of("testUser");
    }

}
