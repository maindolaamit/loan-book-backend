package org.hayo.finance.loanbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanApplicationRejectRequest(
        @NotNull @NotBlank
        String rejectionReason) {
}