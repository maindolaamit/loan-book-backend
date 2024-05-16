package org.hayo.finance.loanbook.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
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
    private final UserService userService;

    @Override
    public LoanApplication submitNewLoanApplication(String customerName, NewLoanApplicationRequest request) {
        val userId = userService.getUserId(customerName);
        return loanService.newLoanApplication(String.valueOf(userId), request);
    }

    @Override
    public List<LoanApplication> getInactiveLoanApplicationsForUser(String customerName) {
        val userId = String.valueOf(userService.getUserId(customerName));
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(userId)
                .status(ApprovalStatus.REJECTED)
                .paymentStatuses(List.of(PaymentStatus.PAID))
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerName) {
        val userId = String.valueOf(userService.getUserId(customerName));
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(userId)
                .status(ApprovalStatus.PENDING)
                .paymentStatuses(List.of(PaymentStatus.PENDING))
                .build();

        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public LoanApplication repayLoanAmount(String customerName, String loanId, @NotNull Double amount) {
        val userId = String.valueOf(userService.getUserId(customerName));
        val loanApplicationId = Long.parseLong(loanId);
        log.info("verifying loan application for customer: {}", customerName);
        if (loanService.applicationNotExists(loanApplicationId)) {
            throw new RecordNotFoundException("Loan Application not found for customer: " + customerName);
        }
        if (amount <= 0.0) {
            throw new InvalidValueException("Invalid amount for loan repayment, should be greater than 0.");
        }
        return loanService.repayLoanAmount(loanApplicationId, amount);
    }

    @Override
    public List<LoanApplication> getAllLoanApplicationsForUser(String customerName) {
        val userId = String.valueOf(userService.getUserId(customerName));
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(userId)
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }
}
