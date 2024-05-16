package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;


public class InvalidUserCredentials extends AbstractWebExceptions {

    private static final String reason = "Invalid user credentials";

    public InvalidUserCredentials() {
        super(reason, HttpStatus.BAD_REQUEST, reason);
    }
}
