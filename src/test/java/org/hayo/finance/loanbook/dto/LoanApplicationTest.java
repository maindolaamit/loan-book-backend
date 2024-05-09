package org.hayo.finance.loanbook.dto;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanApplicationTest {

    private final LoanApplicationMapper loanApplicationMapper = Mappers.getMapper(LoanApplicationMapper.class);

    @Test
    @DisplayName("Test LoanApplication")
    void test1() {
        val now = LocalDate.now();

        val schedule = LoanPaymentSchedule.builder()
                .scheduleId(1L).dueDate(now.plusWeeks(1)).term(1)
                .paymentAmount(100.0).status(PaymentStatus.PENDING)
                .build();

        LoanApplication loanApplication = LoanApplication.builder()
                .applicationId("1").applicationDate(now).status("PENDING").paymentStatus("PENDING")
                .termFrequency("WEEKLY").noOfTerms(12).customerId("10").loanAmount(1000.0)
                .paymentSchedules(List.of(schedule))
                .build();

        assertNotNull(loanApplication);
        assertEquals("1", loanApplication.applicationId());
        assertEquals(now, loanApplication.applicationDate());
        assertEquals("PENDING", loanApplication.status());
        assertEquals("PENDING", loanApplication.paymentStatus());
        assertEquals("WEEKLY", loanApplication.termFrequency());
        assertEquals(12, loanApplication.noOfTerms());
        assertEquals("10", loanApplication.customerId());
        assertEquals(1000.0, loanApplication.loanAmount());
        assertEquals(1, loanApplication.paymentSchedules().size());

    }
}