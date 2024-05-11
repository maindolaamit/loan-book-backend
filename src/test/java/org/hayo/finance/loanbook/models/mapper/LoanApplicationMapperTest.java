package org.hayo.finance.loanbook.models.mapper;

import lombok.val;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LoanApplicationMapperTest {

    @MockBean
    private final LoanPaymentSchedulesMapper paymentSchedulesMapper = Mappers.getMapper(LoanPaymentSchedulesMapper.class);
    @InjectMocks
    private final LoanApplicationMapper loanApplicationMapper = Mappers.getMapper(LoanApplicationMapper.class);
    private final NewLoanApplicationRequest request = NewLoanApplicationRequest.builder().
            terms(10).amount(1000.00)
            .customerId("1")
            .termFrequency("WEEKLY").build();

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
        assertEquals(entity.getLoanAmount(), request.getAmount());
        assertEquals(entity.getNumOfTerms(), request.getTerms());

//        when(paymentSchedulesMapper.toDto(any())).thenReturn(null);
//        val dto = loanApplicationMapper.toDto(entity);
//        assertNotNull(dto);
//        assertNull(dto.applicationId());
//        assertEquals(request.amount(), dto.loanAmount());
//        assertEquals(request.terms(), dto.numOfTerms());
//        assertEquals("PENDING", dto.status());
//        assertEquals("PENDING", dto.paymentStatus());
//        assertEquals("WEEKLY", dto.termFrequency());
//        assertNull(dto.rejectionReason());
//
//        assertEquals(dto.paymentSchedules().size(), request.terms());
    }

}