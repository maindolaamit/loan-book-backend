package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends AbstractWebExceptions {
    public static final String defaultMessage = "Invalid Id provided. Record not found.";

    public RecordNotFoundException(String message) {
        super(message == null ? defaultMessage : message, HttpStatus.NOT_FOUND, "Invalid Id provided. Record not found.");
    }
}
