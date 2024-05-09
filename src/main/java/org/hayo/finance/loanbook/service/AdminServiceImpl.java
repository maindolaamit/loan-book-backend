package org.hayo.finance.loanbook.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.exceptions.RecordNotFoundException;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.hayo.finance.loanbook.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final LoanApplicationRepository repository;
    private final LoanApplicationMapper mapper;

    @Override
    public List<LoanApplication> getAllPendingLoanApplications(String userId) {
        log.info("Getting all pending loan applications for admin: {}", userId);
        val list = repository.findAllByStatus(ApprovalStatus.PENDING).stream().map(mapper::toDto).toList();
        log.info("Found {} pending loan applications.", list.size());
        return list;
    }

    @Override
    public LoanApplication getLoanApplicationsForId(String userId, String applicationId) {
        log.info("Getting loan application for admin: {} and applicationId: {}", userId, applicationId);
        val entity = repository.findById(Long.parseLong(applicationId))
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));
        return mapper.toDto(entity);
    }

    @Override
    public void approveLoanApplicationsForId(String userId, String applicationId) {
        log.info("Approving loan application for admin: {} and applicationId: {}", userId, applicationId);
        val entity = repository.findById(Long.parseLong(applicationId))
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));
        entity.setStatus(ApprovalStatus.APPROVED);
        repository.save(entity);
    }


    @Override
    public void rejectLoanApplicationsForId(String userId, String applicationId, String reason) {
        log.info("Rejecting loan application for admin: {} and applicationId: {}", userId, applicationId);
        val entity = repository.findById(Long.parseLong(applicationId))
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));
        entity.setStatus(ApprovalStatus.REJECTED);
        entity.setRejectionReason(reason);
        entity.getPaymentSchedules().forEach(
                schedule -> schedule.setStatus(PaymentStatus.REJECTED)
        );
        repository.save(entity);
        log.info("Loan application rejected successfully.");
    }
}
