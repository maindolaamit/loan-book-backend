package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends CrudRepository<LoanApplicationEntity, Long>, JpaSpecificationExecutor<LoanApplicationEntity> {
    List<LoanApplicationEntity> findAllByCustomerId(String customerId);

    List<LoanApplicationEntity> findAllByStatus(ApprovalStatus status);
}
