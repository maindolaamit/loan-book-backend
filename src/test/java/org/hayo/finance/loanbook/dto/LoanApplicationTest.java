package org.hayo.finance.loanbook.dto;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hayo.finance.loanbook.utils.LoanUtility.getStringDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanApplicationTest {

    private final LoanApplicationMapper loanApplicationMapper = Mappers.getMapper(LoanApplicationMapper.class);

    @Test
    @DisplayName("Test LoanApplication")
    void test1() {
        val now = LocalDateTime.now();

        val schedule = LoanPaymentSchedule.builder()
                .scheduleId(1L).dueDate(getStringDate(now.plusWeeks(1))).term(1)
                .amountDue(100.0).amountPaid(0.0).status(PaymentStatus.PENDING)
                .build();

        LoanApplication loanApplication = LoanApplication.builder()
                .applicationId("1").applicationDate(getStringDate(now)).status("PENDING").paymentStatus("PENDING")
                .termFrequency("WEEKLY").numOfTerms(12).customerId("10").loanAmount(1000.0)
                .paymentSchedules(List.of(schedule))
                .build();

        assertNotNull(loanApplication);
        assertEquals("1", loanApplication.applicationId());
        assertEquals(getStringDate(now), loanApplication.applicationDate());
        assertEquals("PENDING", loanApplication.status());
        assertEquals("PENDING", loanApplication.paymentStatus());
        assertEquals("WEEKLY", loanApplication.termFrequency());
        assertEquals(12, loanApplication.numOfTerms());
        assertEquals("10", loanApplication.customerId());
        assertEquals(1000.0, loanApplication.loanAmount());
        assertEquals(1, loanApplication.paymentSchedules().size());

    }
}