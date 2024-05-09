package org.hayo.finance.loanbook.repository;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hayo.finance.loanbook.loader.LoanApplicationsLoader.getLoanApplicationEntitiesFromDataFile;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Slf4j
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanApplicationRepositoryTest {

    @Container
    @ServiceConnection
    // static so can be reused by other test classes
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @Autowired
    private LoanApplicationRepository repository;

    @Test
    @DisplayName("Test connection")
    void testConnection() {
        assertThat(postgres.isCreated());
        assertThat(postgres.isRunning());
    }

    @BeforeEach
    void setUp() {
        log.info("JDBC URL: {}", jdbcConnectionDetails.getJdbcUrl());

        List<LoanApplicationEntity> list = null;
        log.info("loading loan entities ...");
        try {
            list = getLoanApplicationEntitiesFromDataFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repository.saveAll(list);
        log.info("no. of records {}", repository.count());
    }

    @Test
    @DisplayName("Find all applications by customer id")
    public void findAllByCustomerId() {
        val applications = repository.findAllByCustomerId("1");
        assertEquals(2, applications.size());
    }

    @Test
    @DisplayName("Entity throws exception when saving with null content")
    public void test1() {
        val build = LoanApplicationEntity.builder().description("Test").build();
        // save entity
        assertThrows(ConstraintViolationException.class, () -> repository.save(build));
    }

    @Test
    @DisplayName("Creating and fetching entity with incidentState active")
    public void test2() {
        val date = LocalDateTime.now();
        val build = LoanApplicationEntity.builder().applicationDate(date).createdAt(date).description("Test")
                .description("Test").createdBy("10").updatedBy("10").loanAmount(1000.0).numOfTerms(12).customerId("10")
                .termFrequency(PaymentFrequency.WEEKLY).status(ApprovalStatus.PENDING).paymentStatus(PaymentStatus.PENDING)
                .build();
        val save = repository.save(build);
        assertNotNull(save.getId());
    }
}