package org.hayo.finance.loanbook.models.mapper;

import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.entity.LoanPaymentScheduleEntity;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.utils.EnumUtility;
import org.hayo.finance.loanbook.utils.LoanUtility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring", uses = {LoanPaymentSchedulesMapper.class})
public interface LoanApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loanAmount", source = "request.amount")
    @Mapping(target = "amountPaid", constant = "0.0")
    @Mapping(target = "numOfTerms", source = "request.terms")
    @Mapping(target = "status", expression = "java(org.hayo.finance.loanbook.models.enums.ApprovalStatus.PENDING)")
    @Mapping(target = "paymentSchedules", expression = "java(getPaymentSchedules(request))")
    @Mapping(target = "paymentStatus", constant = "PENDING")
    @Mapping(target = "termFrequency", constant = "WEEKLY")
    @Mapping(target = "createdBy", source = "request.customerId")
    @Mapping(target = "updatedBy", source = "request.customerId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "applicationDate", expression = "java(java.time.LocalDateTime.now())")
    LoanApplicationEntity toNewEntity(NewLoanApplicationRequest request);

    default List<LoanPaymentScheduleEntity> getPaymentSchedules(NewLoanApplicationRequest request) {
        val payments = LoanUtility.getInstallmentPerTerm(request.getAmount(), request.getTerms());
        List<LoanPaymentScheduleEntity> schedules = new ArrayList<>();
        val applicationDate = LocalDateTime.now();
        val frequency = EnumUtility.fromValue(PaymentFrequency.class, request.getTermFrequency());
        IntStream.range(0, request.getTerms())
                .forEach(i -> {
                    LocalDateTime dueDate = LoanUtility
                            .getNextDueDate(applicationDate, i, frequency);
                    val schedule = LoanPaymentScheduleEntity.builder()
                            .createdAt(applicationDate)
                            .updatedAt(applicationDate)
                            .createdBy(request.getCustomerId())
                            .updatedBy(request.getCustomerId())
                            .dueDate(dueDate)
                            .status(PaymentStatus.PENDING)
                            .amountDue(payments.get(i))
                            .amountPaid(0.0)
                            .term(i + 1)
                            .build();
                    schedules.add(schedule);
                });
        return schedules;
    }

    @Mapping(target = "applicationId", source = "id")
    @Mapping(target = "applicationDate", expression = "java(org.hayo.finance.loanbook.utils.LoanUtility.getStringDate(entity.getApplicationDate()))")
    LoanApplication toDto(LoanApplicationEntity entity);
}
