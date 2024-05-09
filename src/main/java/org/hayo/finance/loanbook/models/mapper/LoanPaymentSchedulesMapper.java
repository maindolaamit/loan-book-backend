package org.hayo.finance.loanbook.models.mapper;

import org.hayo.finance.loanbook.dto.LoanPaymentSchedule;
import org.hayo.finance.loanbook.models.entity.LoanPaymentScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanPaymentSchedulesMapper {
    @Mapping(target = "scheduleId", source = "entity.id")
    @Mapping(target = "dueDate", expression = "java(org.hayo.finance.loanbook.utils.LoanUtility.getStringDate(entity.getDueDate()))")
    LoanPaymentSchedule toDto(LoanPaymentScheduleEntity entity);
}
