package org.hayo.finance.loanbook.service;

import jakarta.validation.constraints.NotNull;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;

import java.util.List;

public interface CustomerService {

    LoanApplication submitNewLoanApplication(String customerName, NewLoanApplicationRequest request);

    List<LoanApplication> getInactiveLoanApplicationsForUser(String customerName);

    List<LoanApplication> getAllActiveLoanApplicationsForUser(String customerName);

    LoanApplication repayLoanAmount(String customerName, String loanApplicationId, @NotNull Double amount);

    List<LoanApplication> getAllLoanApplicationsForUser(String customerName);
}
