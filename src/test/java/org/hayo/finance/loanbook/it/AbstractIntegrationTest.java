package org.hayo.finance.loanbook.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
public class AbstractIntegrationTest {

    @Container
    protected static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.0-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

}
