package org.hayo.finance.loanbook.repository;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class LoanApplicationRepositoryTest {

    @Autowired
    private LoanApplicationRepository repository;

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
                .description("Test").createdBy("1").updatedBy("1").loanAmount(1000.0).numOfTerms(12).customerId(1L)
                .termFrequency(PaymentFrequency.WEEKLY).status(ApprovalStatus.PENDING).paymentStatus(PaymentStatus.PENDING)
                .build();
        val save = repository.save(build);
        assertNotNull(save.getId());
    }

}