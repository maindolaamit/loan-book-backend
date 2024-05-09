package org.hayo.finance.loanbook.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanApplicationRejectRequestTest {
    @Test
    @DisplayName("Test LoanApplicationRejectRequest")
    void test1() {
        LoanApplicationRejectRequest loanApplicationRejectRequest = new LoanApplicationRejectRequest("reason");
        assertNotNull(loanApplicationRejectRequest);
        assertEquals("reason", loanApplicationRejectRequest.rejectionReason());
    }

}