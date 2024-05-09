package org.hayo.finance.loanbook.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.exceptions.InvalidValueException;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.hayo.finance.loanbook.repository.LoanApplicationRepository;
import org.hayo.finance.loanbook.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hayo.finance.loanbook.repository.LoanApplicationsSpecifications.createSpecifications;

@Slf4j
@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    LoanApplicationRepository repository;
    LoanApplicationMapper mapper;

    @Override
    public LoanApplication newLoanApplication(String userId, LoanApplicationRequest request) {
        val newRequest = LoanApplicationRequest.copyWithUserId(request, userId);
        // create loan schedules based on frequency, amount and terms
        log.info("Creating new loan application");
        LoanApplicationEntity entity = mapper.toNewEntity(newRequest);
        // save loan application
        entity = repository.save(entity);
        log.info("New loan application created successfully");
        return mapper.toDto(entity);
    }

    @Override
    public String repayLoanAmount(String userId) {
        return null;
    }

    @Override
    public List<LoanApplication> searchLoanApplications(SearchLoanApplicationsRequest request) {
        if (request == null) throw new InvalidValueException("Null optionValue passed for search request");
        val specs = createSpecifications(request);
        log.info("Searching loan applications forms with criteria: {}", specs.toString());
        return repository.findAll(specs)
                .stream()
                .map(mapper::toDto).toList();
    }
}
