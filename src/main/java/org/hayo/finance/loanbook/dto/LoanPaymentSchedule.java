package org.hayo.finance.loanbook.dto;

import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;

public record LoanPaymentSchedule(
        Long scheduleId,
        LocalDate dueDate, Double paymentAmount,
        PaymentStatus status) {
}
