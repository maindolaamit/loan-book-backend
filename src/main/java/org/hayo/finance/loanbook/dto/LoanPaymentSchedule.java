package org.hayo.finance.loanbook.dto;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

@Builder
public record LoanPaymentSchedule(
        Long scheduleId, Integer term,
        String dueDate, Double amountDue, Double amountPaid,
        PaymentStatus status) {
}
