package org.hayo.finance.loanbook.service;

import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;

import java.util.List;

public interface LoanService {
    LoanApplication newLoanApplication(String userId, LoanApplicationRequest request);

    String repayLoanAmount(String userId);

    List<LoanApplication> searchLoanApplications(SearchLoanApplicationsRequest request);
}
