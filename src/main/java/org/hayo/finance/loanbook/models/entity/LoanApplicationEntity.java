package org.hayo.finance.loanbook.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cust_loan_applications")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class LoanApplicationEntity extends AbstractEntity {
    @Id
    @Column(name = "loan_application_id")
    private Long id;
    private String customerId;
    LocalDateTime applicationDate;
    @NotNull(message = "Loan Amount is required")
    @Min(value = 100, message = "Min Loan amount must be 100")
    Double loanAmount;
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment Status is required")
    private PaymentStatus paymentStatus;
    @NotNull(message = "Number of Terms is required")
    @Min(value = 1, message = "Min number of terms must be 1")
    private Integer numOfTerms;
    @NotNull(message = "Term Frequency is required")
    @Enumerated(EnumType.STRING)
    private PaymentFrequency termFrequency;
    private String description;
    private String rejectionReason;

    @OneToMany(mappedBy = "loanApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<LoanPaymentScheduleEntity> paymentSchedules;
}
