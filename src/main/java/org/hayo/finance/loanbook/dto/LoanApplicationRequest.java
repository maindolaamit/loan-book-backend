package org.hayo.finance.loanbook.dto;

import lombok.Builder;

@Builder
public record LoanApplicationRequest(Double loanAmount, String noOfTerms, String termFrequency) {
}
