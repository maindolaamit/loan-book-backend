package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanApplicationRepository extends CrudRepository<LoanApplicationEntity, Long> {
    List<LoanApplicationEntity> findAllByCustomerId(String custId);

    List<LoanApplicationEntity> findAllByStatus(ApprovalStatus status);
}
