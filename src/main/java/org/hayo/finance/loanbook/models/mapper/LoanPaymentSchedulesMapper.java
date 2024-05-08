package org.hayo.finance.loanbook.models.mapper;

import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.dto.LoanPaymentSchedule;
import org.hayo.finance.loanbook.models.entity.LoanPaymentScheduleEntity;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.hayo.finance.loanbook.utils.EnumUtility;
import org.hayo.finance.loanbook.utils.LoanUtility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface LoanPaymentSchedulesMapper {
    @Mapping(target = "id", expression = "java(null)")
    LoanPaymentScheduleEntity toNewEntity(LoanPaymentSchedule loanPaymentSchedule);


}
