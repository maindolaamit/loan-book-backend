package org.hayo.finance.loanbook.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record LoanApplication(
        String applicationId, String applicationDate, Double loanAmount, Integer numOfTerms,
        String termFrequency, String status, String paymentStatus, String customerId, String rejectionReason,
        List<LoanPaymentSchedule> paymentSchedules
) {
}
