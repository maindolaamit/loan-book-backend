package org.hayo.finance.loanbook.models;

import lombok.Builder;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;

import java.time.LocalDate;

@Builder
public record SearchLoanApplicationsRequest(
        String customerId,
        ApprovalStatus status,
        Double minAmount,
        Double maxAmount,
        LocalDate startDate,
        LocalDate endDate
) {
}
