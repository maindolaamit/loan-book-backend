package org.hayo.finance.loanbook.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
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
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerId) {
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(customerId)
                .status(ApprovalStatus.PENDING)
                .build();

        return loanService.searchLoanApplications(searchRequest);
    }

    @Override
    public String repayLoanAmount(String customerId) {
        return loanService.repayLoanAmount(customerId);
    }

    @Override
    public List<LoanApplication> getAllLoanApplicationsForUser(String customerId) {
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .customerId(customerId)
                .build();
        return loanService.searchLoanApplications(searchRequest);
    }
}
