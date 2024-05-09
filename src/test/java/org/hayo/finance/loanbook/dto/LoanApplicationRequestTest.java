package org.hayo.finance.loanbook.dto;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanApplicationRequestTest {

    @Test
    @DisplayName("Test LoanApplicationRequest")
    void test1() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest(1000.00, 3,
                "1", "WEEKLY", "test");
        assertNotNull(loanApplicationRequest);
        assertEquals("1", loanApplicationRequest.customerId());
        assertEquals("1", loanApplicationRequest.customerId());
        assertEquals(1000.0, loanApplicationRequest.amount());
        assertEquals(3, loanApplicationRequest.terms());
        assertEquals("WEEKLY", loanApplicationRequest.termFrequency());


        val anotherRequest = LoanApplicationRequest.copyWithUserId(loanApplicationRequest, "2");
        assertEquals("2", anotherRequest.customerId());
    }

}