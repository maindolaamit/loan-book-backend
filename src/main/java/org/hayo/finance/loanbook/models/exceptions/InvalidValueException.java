package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;


public class InvalidValueException extends AbstractWebExceptions {

    private static String getReason(String reason) {
        return reason != null ? " : " + reason : "";
    }

    public InvalidValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST, message);
    }

    public InvalidValueException(String message, String reason) {
        super(message, HttpStatus.BAD_REQUEST, getReason(reason));
    }
}
