package org.hayo.finance.loanbook.dto;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SearchLoanApplicationsRequest(
        String customerId,
        List<PaymentStatus> paymentStatuses,
        ApprovalStatus status,
        Double minAmount,
        Double maxAmount,
        LocalDate startDate,
        LocalDate endDate
) {
}
