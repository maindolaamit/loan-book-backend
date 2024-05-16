package org.hayo.finance.loanbook.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.dto.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.entity.LoanPaymentScheduleEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.exceptions.InvalidLoanStatusException;
import org.hayo.finance.loanbook.models.exceptions.InvalidValueException;
import org.hayo.finance.loanbook.models.exceptions.RecordNotFoundException;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.hayo.finance.loanbook.repository.LoanApplicationRepository;
import org.hayo.finance.loanbook.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hayo.finance.loanbook.repository.LoanApplicationsSpecifications.createSpecifications;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {
    LoanApplicationRepository repository;
    LoanApplicationMapper mapper;
    UserService userService;

    @Override
    public LoanApplication newLoanApplication(String userName, NewLoanApplicationRequest request) {
        String userId = String.valueOf(userService.getUserId(userName));
        val newRequest = NewLoanApplicationRequest.copyWithUserId(request, userId);
        // create loan schedules based on frequency, amount and terms
        log.info("Creating new loan application");
        LoanApplicationEntity entity = mapper.toNewEntity(newRequest);
        // save loan application
        entity = repository.save(entity);
        log.info("New loan application created successfully");
        return mapper.toDto(entity);
    }

    @Override
    public LoanApplication repayLoanAmount(Long loanApplicationId, @NotNull Double repayAmount) {
        val entity = repository.findById(loanApplicationId)
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + loanApplicationId));

        // check if the loan application is approved
        if (entity.getStatus() != ApprovalStatus.APPROVED) {
            throw new InvalidLoanStatusException("Loan application is not eligible for repay.");
        }

        if (entity.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new InvalidLoanStatusException("Loan is already paid.");
        }

        if (repayAmount > entity.getLoanAmount() - entity.getAmountPaid()) {
            throw new InvalidValueException("Repay amount is greater than the loan amount.");
        }

        // if repay amount = amount left to pay, mark the loan as paid
        if (repayAmount == entity.getLoanAmount() - entity.getAmountPaid()) {
            entity.setPaymentStatus(PaymentStatus.PAID);
            entity.setAmountPaid(entity.getLoanAmount());
            entity.getPaymentSchedules().forEach(
                    schedule -> {
                        schedule.setStatus(PaymentStatus.PAID);
                        schedule.setAmountPaid(schedule.getAmountDue());
                    }
            );
        }

        // Repay the loan
        var totalBalanceLeft = entity.getLoanAmount() - entity.getAmountPaid();
        val schedules = entity.getPaymentSchedules();
        for (final LoanPaymentScheduleEntity schedule : schedules) {
            if (schedule.getStatus() != PaymentStatus.PAID) {
                // calculate the amount to pay
                val amountToPay = schedule.getAmountDue() - schedule.getAmountPaid();
                // check if the amount to pay is less than the repay amount
                // if yes, mark the schedule as paid and move to the next schedule
                // if no, mark the schedule as partial
                if (amountToPay <= repayAmount) {
                    schedule.setStatus(PaymentStatus.PAID);
                    schedule.setAmountPaid(amountToPay);
                    repayAmount -= amountToPay;
                } else {
                    schedule.setAmountPaid(amountToPay);
                    schedule.setStatus(PaymentStatus.PARTIAL);
                    repayAmount -= amountToPay;
                }
                totalBalanceLeft -= schedule.getAmountPaid();
                // check if the repay amount is zero
                // if yes, break the loop
                if (repayAmount == 0) {
                    break;
                }
            }
        }

        // check if the total balance left is zero
        // if yes, mark the loan as paid
        if (totalBalanceLeft == 0) {
            entity.setPaymentStatus(PaymentStatus.PAID);
            entity.setAmountPaid(entity.getLoanAmount());
        } else {
            entity.setAmountPaid(entity.getLoanAmount() - totalBalanceLeft);
        }
        // save the entity
        val newEntity = repository.save(entity);
        return mapper.toDto(newEntity);
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

    @Override
    public Optional<LoanApplication> findApplicationById(long applicationId) {
        return repository.findById(applicationId)
                .map(mapper::toDto);
    }

    @Override
    public boolean rejectLoanApplication(long applicationId, String reason) {
        log.info("Rejecting loan application for applicationId: {}", applicationId);
        val entity = repository.findById(applicationId)
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));

        if (entity.getStatus() != ApprovalStatus.PENDING) {
            throw new InvalidLoanStatusException("Loan application already Rejected.");
        }

        entity.setStatus(ApprovalStatus.REJECTED);
        entity.setRejectionReason(reason);
//        entity.getPaymentSchedules().forEach(
//                schedule -> schedule.setStatus(PaymentStatus.REJECTED)
//        );
        repository.save(entity);
        return true;
    }

    @Override
    public boolean approveLoanApplication(long applicationId) {
        log.info("Approving loan application for applicationId: {}", applicationId);
        val entity = repository.findById(applicationId)
                .orElseThrow(() -> new RecordNotFoundException("Loan Application not found for id: " + applicationId));

        if (entity.getStatus() != ApprovalStatus.PENDING) {
            throw new InvalidLoanStatusException("Loan application already approved.");
        }

        entity.setStatus(ApprovalStatus.APPROVED);
        repository.save(entity);
        return true;
    }

    @Override
    public boolean applicationNotExists(Long applicationId) {
        val byId = repository.findById(applicationId);
        return byId.isEmpty();
    }

}
