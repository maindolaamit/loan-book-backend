package org.hayo.finance.loanbook.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    @Override
    public String createLoanRequest(String admin, LoanApplicationRequest request) {
        return null;
    }

    @Override
    public List<LoanApplication> getLoanApplicationsForUser(String userId) {
        return null;
    }

    @Override
    public String repayLoanAmount(String userId) {
        return null;
    }
}
