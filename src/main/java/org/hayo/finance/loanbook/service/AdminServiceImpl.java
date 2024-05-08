package org.hayo.finance.loanbook.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Override
    public List<LoanApplication> getAllPendingLoanApplications(String userId) {
        return null;
    }

    @Override
    public LoanApplication getLoanApplicationsForId(String userId, String applicationId) {
        return null;
    }
}
