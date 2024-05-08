package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends AbstractWebExceptions {
    public static final String REASON = "Invalid Id provided. Record not found.";
    public RecordNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "Invalid Id provided. Record not found.");
    }
}
