package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.dto.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoanApplicationsSpecificationsTest {
    private final SearchLoanApplicationsRequest request = SearchLoanApplicationsRequest.builder()
            .customerId("1")
            .startDate(LocalDate.now().minusMonths(1))
            .endDate(LocalDate.now().plusDays(1))
            .minAmount(1000.0)
            .maxAmount(100000.0)
            .status(ApprovalStatus.PENDING)
            .build();

    @Test
    @DisplayName("Test LoanApplicationsSpecifications")
    void test1() {
        var spec = LoanApplicationsSpecifications.createSpecifications(request);
        assertTrue(spec != null);
    }

}