package org.hayo.finance.loanbook.dto;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;

@Builder
public record LoanPaymentSchedule(
        Long scheduleId, Integer term,
        LocalDate dueDate, Double paymentAmount,
        PaymentStatus status) {
}
