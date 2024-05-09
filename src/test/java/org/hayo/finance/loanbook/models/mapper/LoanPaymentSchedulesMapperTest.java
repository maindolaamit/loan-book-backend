package org.hayo.finance.loanbook.models.mapper;

import lombok.val;
import org.hayo.finance.loanbook.models.entity.LoanPaymentScheduleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanPaymentSchedulesMapperTest {
    private final LoanPaymentSchedulesMapper paymentScheduleMapper = Mappers.getMapper(LoanPaymentSchedulesMapper.class);
    private final LoanPaymentScheduleEntity request = LoanPaymentScheduleEntity.builder().
            paymentAmount(1000.00).dueDate(LocalDateTime.now()).term(1).build();

    @Test
    @DisplayName("Test toNewEntity")
    void toNewEntity() {
        val entity = paymentScheduleMapper.toDto(request);
        assertNotNull(entity);
        assertEquals(request.getAmountDue(), entity.amountDue());
        assertEquals(request.getTerm(), entity.term());
    }

}