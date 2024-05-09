package org.hayo.finance.loanbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RepayRequest(
        @NotNull(message = "Loan Id cannot be null")
        @NotBlank
        String loanId,
        @NotNull(message = "Schedule Id cannot be null")
        @NotBlank
        String scheduleId,
        @NotNull(message = "Amount cannot be null")
        @NotBlank
        String amount) {
}
