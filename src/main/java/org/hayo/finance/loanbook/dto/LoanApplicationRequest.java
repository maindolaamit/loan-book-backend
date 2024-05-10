package org.hayo.finance.loanbook.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.val;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;

@Builder
public record LoanApplicationRequest(
        @NotNull
        @Min(value = 100, message = "Min Loan amount must be 100")
        Double amount,
        @NotNull
        @Min(value = 1, message = "Min number of terms must be 1")
        Integer terms,
        String customerId,
        String termFrequency, String description) {
    public static LoanApplicationRequest copyWithUserId(LoanApplicationRequest request, String userId) {
        val frequency = request.termFrequency() == null || request.termFrequency().isEmpty() ? PaymentFrequency.WEEKLY.name() : request.termFrequency();
        return LoanApplicationRequest.builder()
                .customerId(userId)
                .amount(request.amount())
                .terms(request.terms())
                .termFrequency(frequency)
                .build();
    }
}
