package org.hayo.finance.loanbook.service;

import org.hayo.finance.loanbook.dto.LoanApplication;

import java.util.List;

public interface AdminService {
    List<LoanApplication> getAllPendingLoanApplications(String userId);

    LoanApplication getLoanApplicationsForId(String userId, String applicationId);

    void approveLoanApplicationsForId(String userId, String applicationId);

    void rejectLoanApplicationsForId(String userId, String applicationId, String reason);
}
