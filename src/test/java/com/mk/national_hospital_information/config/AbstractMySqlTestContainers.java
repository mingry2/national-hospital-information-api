package com.mk.national_hospital_information.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@ContextConfiguration(initializers = AbstractMySqlTestContainers.Initializer.class)
public abstract class AbstractMySqlTestContainers {

    @Value("${jwt.token.secret}") String secret;
    private static final MySQLContainer<?> MYSQL_CONTAINER;

    static {
        MYSQL_CONTAINER = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withReuse(true);
        MYSQL_CONTAINER.start();
    }

    public static class Initializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                "spring.datasource.url=" + MYSQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + MYSQL_CONTAINER.getUsername(),
                "spring.datasource.password=" + MYSQL_CONTAINER.getPassword(),
                "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
            ).applyTo(context.getEnvironment());
        }
    }
}
