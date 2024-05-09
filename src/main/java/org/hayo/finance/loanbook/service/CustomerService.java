package org.hayo.finance.loanbook.service;

import jakarta.validation.constraints.NotNull;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;

import java.util.List;

public interface CustomerService {

    LoanApplication submitNewLoanApplication(String userId, LoanApplicationRequest request);

    List<LoanApplication> getInactiveLoanApplicationsForUser(String customerId);

    List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerId);

    LoanApplication repayLoanAmount(String customerId, String loanApplicationId, @NotNull Double amount);

    List<LoanApplication> getAllLoanApplicationsForUser(String customerId);
}
