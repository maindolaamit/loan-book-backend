package org.hayo.finance.loanbook.service;

import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;

import java.util.List;

public interface LoanService {
    String createLoanRequest(String admin, LoanApplicationRequest request);

    List<LoanApplication> getLoanApplicationsForUser(String userId);

    String repayLoanAmount(String userId);
}
