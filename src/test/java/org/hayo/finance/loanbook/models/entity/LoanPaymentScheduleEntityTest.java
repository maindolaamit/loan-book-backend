package org.hayo.finance.loanbook.models.entity;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.utils.LoanUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanPaymentScheduleEntityTest {

    @Test
    @DisplayName("Test LoanPaymentScheduleEntity")
    void test() {
        LoanPaymentScheduleEntity entity = new LoanPaymentScheduleEntity();
        assertNotNull(entity);

        LoanPaymentScheduleEntity schedule = LoanPaymentScheduleEntity.builder().build();
        assertNull(schedule.getId());

        val now = LocalDateTime.now();
        LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
                .id(20L)
                .applicationDate(now).updatedAt(now).updatedBy("10").createdAt(now).createdBy("10")
                .description("Test").rejectionReason("").paymentStatus(PaymentStatus.PENDING)
                .status(ApprovalStatus.PENDING).termFrequency(PaymentFrequency.WEEKLY)
                .customerId("1").numOfTerms(12).loanAmount(1000.0)
                .build();

        schedule = LoanPaymentScheduleEntity.builder()
                .updatedAt(now).updatedBy("10").createdAt(now).createdBy("10")
                .status(PaymentStatus.PENDING).dueDate(LoanUtility.getNextDueDate(now, 1))
                .amountDue(100.0).amountPaid(0.0).loanApplication(loanApplicationEntity)
                .build();

        loanApplicationEntity.setPaymentSchedules(List.of(schedule));

        assertNull(schedule.getId());
        assertEquals(now, schedule.getCreatedAt());
        assertEquals(now, schedule.getUpdatedAt());
        assertEquals("10", schedule.getCreatedBy());
        assertEquals("10", schedule.getUpdatedBy());
        assertEquals(PaymentStatus.PENDING, schedule.getStatus());
        assertEquals(LoanUtility.getNextDueDate(now, 1), schedule.getDueDate());
        assertEquals(100.0, schedule.getAmountDue());
        assertEquals(loanApplicationEntity, schedule.getLoanApplication());
        assertEquals(loanApplicationEntity.getPaymentSchedules().get(0), schedule);
    }
}