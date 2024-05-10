package org.hayo.finance.loanbook.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.exceptions.InvalidValueException;
import org.hayo.finance.loanbook.models.exceptions.RecordNotFoundException;
import org.hayo.finance.loanbook.service.CustomerService;
import org.hayo.finance.loanbook.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final LoanService loanService;

    @Override
    public LoanApplication submitNewLoanApplication(String customerId, LoanApplicationRequest request) {
        return loanService.newLoanApplication(customerId, request);
    }

    @Override
    public List<LoanApplication> getInactiveLoanApplicationsForUser(String customerId) {
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(customerId)
                .status(ApprovalStatus.REJECTED)
                .paymentStatuses(List.of(PaymentStatus.PAID))
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerId) {
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(customerId)
                .status(ApprovalStatus.PENDING)
                .paymentStatuses(List.of(PaymentStatus.PENDING))
                .build();

        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public LoanApplication repayLoanAmount(String customerId, String loanId, @NotNull Double amount) {
        val loanApplicationId = Long.parseLong(loanId);
        log.info("verifying loan application for customer: {}", customerId);
        if (loanService.applicationNotExists(loanApplicationId)) {
            throw new RecordNotFoundException("Loan Application not found for customer: " + customerId);
        }
        if (amount <= 0.0) {
            throw new InvalidValueException("Invalid amount for loan repayment, should be greater than 0.");
        }
        return loanService.repayLoanAmount(loanApplicationId, amount);
    }

    @Override
    public List<LoanApplication> getAllLoanApplicationsForUser(String customerId) {
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(customerId)
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }
}
