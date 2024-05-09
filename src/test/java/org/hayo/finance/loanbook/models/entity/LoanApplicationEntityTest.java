package org.hayo.finance.loanbook.models.entity;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationEntityTest {

    @Test
    @DisplayName("Test LoanApplicationEntity")
    void test1() {
        LoanApplicationEntity loanApplicationEntity = new LoanApplicationEntity();
        assertNotNull(loanApplicationEntity);

        LoanApplicationEntity entity = LoanApplicationEntity.builder().build();
        assertNull(entity.getId());


        val now = LocalDateTime.now();
        entity = LoanApplicationEntity.builder()
                .description("Test").rejectionReason("")
                .paymentStatus(PaymentStatus.PENDING)
                .status(ApprovalStatus.PENDING)
                .termFrequency(PaymentFrequency.WEEKLY)
                .numOfTerms(12).customerId(10L).loanAmount(1000.0)
                .createdBy("10").createdAt(now).updatedAt(now).updatedBy("10")
                .build();

        assertNull(entity.getId());
        assertNull(entity.getPaymentSchedules());
        assertEquals("Test", entity.getDescription());
        assertEquals("", entity.getRejectionReason());
        assertEquals(PaymentStatus.PENDING, entity.getPaymentStatus());
        assertEquals(ApprovalStatus.PENDING, entity.getStatus());
        assertEquals(PaymentFrequency.WEEKLY, entity.getTermFrequency());
        assertEquals(12, entity.getNumOfTerms());
        assertEquals(10L, entity.getCustomerId());
        assertEquals(1000.0, entity.getLoanAmount());
        assertEquals("10", entity.getCreatedBy());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertEquals("10", entity.getUpdatedBy());
    }

}