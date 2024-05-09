package org.hayo.finance.loanbook.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cust_loan_payment_schedules")
public class LoanPaymentScheduleEntity extends AbstractEntity {
    @Id
    @Column(name = "loan_schedule_id")
    private Long id;
    private LocalDateTime dueDate;
    private Integer term;
    private Double amountDue;
    //    private Double principal;
//    private Double interest;
    private Double amountPaid;
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplicationEntity loanApplication;
}
