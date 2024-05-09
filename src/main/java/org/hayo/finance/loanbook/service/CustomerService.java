package org.hayo.finance.loanbook.service;

import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;

import java.util.List;

public interface CustomerService {

    LoanApplication submitNewLoanApplication(String userId, LoanApplicationRequest request);

    List<LoanApplication> getInactiveLoanApplicationsForUser(String customerId);

    List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerId);

    String repayLoanAmount(String userId);

    List<LoanApplication> getAllLoanApplicationsForUser(String customerId);
}
