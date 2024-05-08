package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;

public interface LoanApplicationRepository extends CrudRepository<LoanApplicationEntity, Long> {
    List<LoanApplicationEntity> findAllByCustomerId(Long custId);
}
