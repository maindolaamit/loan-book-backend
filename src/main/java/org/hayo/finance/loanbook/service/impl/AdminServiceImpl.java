package org.hayo.finance.loanbook.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.exceptions.RecordNotFoundException;
import org.hayo.finance.loanbook.service.AdminService;
import org.hayo.finance.loanbook.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final LoanService loanService;

    @Override
    public List<LoanApplication> getAllPendingLoanApplications(String userId) {
        log.info("Getting all pending loan applications for admin: {}", userId);
        SearchLoanApplicationsRequest searchRequest = SearchLoanApplicationsRequest.builder()
                .status(ApprovalStatus.PENDING)
                .build();
        val list = loanService.searchLoanApplications(searchRequest);
        log.info("Found {} pending loan applications.", list.size());
        return list;
    }

    @Override
    public LoanApplication getLoanApplicationsForId(String userId, String applicationId) {
        log.info("Getting loan application for admin: {} and applicationId: {}", userId, applicationId);
        return loanService.findApplicationById(Long.parseLong(applicationId))
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));
    }

    @Override
    public void approveLoanApplicationsForId(String userId, String applicationId) {
        if (loanService.applicationNotExists(Long.parseLong(applicationId)))
            throw new RecordNotFoundException("Loan Application not found for id: " + applicationId);

        log.info("Approving loan application for admin: {} and applicationId: {}", userId, applicationId);
        loanService.approveLoanApplication(Long.parseLong(applicationId));
        log.info("Loan application approved successfully.");
    }


    @Override
    public void rejectLoanApplicationsForId(String userId, String applicationId, String reason) {
        if (loanService.applicationNotExists(Long.parseLong(applicationId)))
            throw new RecordNotFoundException("Loan Application not found for id: " + applicationId);

        loanService.rejectLoanApplication(Long.parseLong(applicationId), reason);
        log.info("Loan application rejected successfully.");
    }

    @Override
    public List<LoanApplication> getAllLoanApplications(String userId) {
        val request = SearchLoanApplicationsRequest.builder().build();
        return loanService.searchLoanApplications(request);
    }
}
