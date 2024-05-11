package org.hayo.finance.loanbook.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewLoanApplicationRequest {
    @NotNull
    @Min(value = 100, message = "Min Loan amount must be 100")
    Double amount;
    @NotNull
    @Min(value = 1, message = "Min number of terms must be 1")
    Integer terms;
    @Builder.Default
    String customerId = null;
    @Builder.Default
    String termFrequency = PaymentFrequency.WEEKLY.name();
    @Builder.Default
    String description = "new Loan Request";


    public static NewLoanApplicationRequest copyWithUserId(NewLoanApplicationRequest request, String userId) {
        return NewLoanApplicationRequest.builder()
                .customerId(userId)
                .amount(request.getAmount())
                .terms(request.getTerms())
                .termFrequency(request.getTermFrequency())
                .build();
    }
}
