package org.hayo.finance.loanbook.models.mapper;

import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationMapperTest {

    private final LoanApplicationMapper loanApplicationMapper = Mappers.getMapper(LoanApplicationMapper.class);
    private final LoanApplicationRequest request = LoanApplicationRequest.builder().
            terms(10).amount(1000.00).customerId("1").termFrequency("WEEKLY").build();

    @Test
    @DisplayName("Test toNewEntity")
    void toNewEntity() {
        val entity = loanApplicationMapper.toNewEntity(request);
        assertNotNull(entity);
    }

    @Test
    @DisplayName("Test toDto")
    void toDto() {
        val entity = loanApplicationMapper.toNewEntity(request);
        assertNotNull(entity);
        val dto = loanApplicationMapper.toDto(null);
        assertNotNull(dto);
        assertNull(dto.applicationId());
        assertEquals(dto.loanAmount(), request.amount());
        assertEquals(dto.noOfTerms(), request.terms());
        assertEquals(dto.status(), "PENDING");
        assertEquals(dto.paymentStatus(), "PENDING");
        assertEquals(dto.termFrequency(), "WEEKLY");
        assertNull(dto.rejectionReason());

        assertEquals(dto.paymentSchedules().size(), request.terms());
    }

}