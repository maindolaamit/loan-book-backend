package org.hayo.finance.loanbook.dto;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

@Builder
public record LoanApplication(
        String applicationId, LocalDate applicationDate, Double loanAmount, Integer noOfTerms,
        String termFrequency, String status, String paymentStatus, String customerId, String rejectionReason,
        List<LoanPaymentSchedule> paymentSchedules
) {
}
