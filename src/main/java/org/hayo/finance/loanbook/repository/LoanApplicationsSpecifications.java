package org.hayo.finance.loanbook.repository;

import lombok.val;
import org.hayo.finance.loanbook.models.SearchLoanApplicationsRequest;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.utils.LoanUtility;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanApplicationsSpecifications {

    public static Specification<LoanApplicationEntity> getSpecForMinAmount(Double minAmount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("loanAmount"), minAmount);
    }

    public static Specification<LoanApplicationEntity> getSpecForMaxAmount(Double maxAmount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("loanAmount"), maxAmount);
    }

    public static Specification<LoanApplicationEntity> getSpecForStatus(ApprovalStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<LoanApplicationEntity> getSpecForPaymentStatus(PaymentStatus status) {
        return (root, query, criteriaBuilder) -> {
            val join = root.join("paymentSchedules");
            return criteriaBuilder.equal(join.get("status"), status);
        };
    }

    public static Specification<LoanApplicationEntity> getSpecForCustomerId(String customerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"), customerId);
    }

    private static Specification<LoanApplicationEntity> getSpecForCreationDateFrom(LocalDate value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), value);
    }

    private static Specification<LoanApplicationEntity> getSpecForCreationDateTo(LocalDate value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), value);
    }

    public static Specification<LoanApplicationEntity> createSpecifications(SearchLoanApplicationsRequest request) {
        // default spec 1=1
        if (request == null) return null;
        Specification<LoanApplicationEntity> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.literal(1), 1));

        if (request.paymentStatuses() != null && !request.paymentStatuses().isEmpty()) {
            for (val status : request.paymentStatuses()) {
                if (status == null)
                    throw new IllegalArgumentException("Invalid status value");
                spec = spec.and(getSpecForPaymentStatus(status));
            }
        }
        if (request.minAmount() != null)
            spec = spec.and(getSpecForMinAmount(request.minAmount()));
        if (request.maxAmount() != null)
            spec = spec.and(getSpecForMaxAmount(request.maxAmount()));
        if (request.status() != null)
            spec = spec.and(getSpecForStatus(request.status()));
        if (!LoanUtility.isNullOrEmpty(request.customerId()))
            spec = spec.and(getSpecForCustomerId(request.customerId()));
        if (request.startDate() != null)
            spec = spec.and(getSpecForCreationDateFrom(request.startDate()));
        if (request.endDate() != null)
            spec = spec.and(getSpecForCreationDateTo(request.endDate()));

        return spec;
    }
}
