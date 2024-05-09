package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoanStatusException extends AbstractWebExceptions {
    private static final String defaultMessage = "No Results found given Criteria.";

    public InvalidLoanStatusException(String message) {
        super(message == null ? defaultMessage : message, HttpStatus.NOT_FOUND, "Invalid Loan Status for application for operation.");
    }
}
