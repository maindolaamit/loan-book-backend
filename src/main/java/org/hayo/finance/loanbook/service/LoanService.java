package org.hayo.finance.loanbook.service;

import jakarta.validation.constraints.NotNull;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.dto.SearchLoanApplicationsRequest;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    LoanApplication newLoanApplication(String userId, NewLoanApplicationRequest request);

    LoanApplication repayLoanAmount(Long loanApplicationId, @NotNull Double request);

    List<LoanApplication> searchLoanApplications(SearchLoanApplicationsRequest request);

    Optional<LoanApplication> findApplicationById(long applicationId);

    boolean rejectLoanApplication(long applicationId, String reason);

    boolean approveLoanApplication(long applicationId);

    boolean applicationNotExists(Long applicationId);
}
