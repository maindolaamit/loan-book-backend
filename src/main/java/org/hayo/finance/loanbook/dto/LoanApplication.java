package org.hayo.finance.loanbook.dto;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

@Builder
public record LoanApplication(
        String applicationId, LocalDate applicationDate, Double loanAmount, String noOfTerms,
        String termFrequency, PaymentStatus status, String userId, String rejectionReason,
        List<PaymentSchedule> schedules
) {
}
