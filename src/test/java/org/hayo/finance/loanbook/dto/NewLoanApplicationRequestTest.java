package org.hayo.finance.loanbook.dto;

import lombok.val;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewLoanApplicationRequestTest {

    @Test
    @DisplayName("Test LoanApplicationRequest")
    void test1() {
        NewLoanApplicationRequest loanApplicationRequest = new NewLoanApplicationRequest(1000.00, 3,
                "1", "WEEKLY", "test");
        assertNotNull(loanApplicationRequest);
        assertEquals("1", loanApplicationRequest.getCustomerId());
        assertEquals("1", loanApplicationRequest.getCustomerId());
        assertEquals(1000.0, loanApplicationRequest.getAmount());
        assertEquals(3, loanApplicationRequest.getTerms());
        assertEquals("WEEKLY", loanApplicationRequest.getTermFrequency());


        val anotherRequest = NewLoanApplicationRequest.copyWithUserId(loanApplicationRequest, "2");
        assertEquals("2", anotherRequest.getCustomerId());
    }

}