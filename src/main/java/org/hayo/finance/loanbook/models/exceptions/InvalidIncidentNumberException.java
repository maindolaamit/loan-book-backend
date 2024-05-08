package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidIncidentNumberException extends AbstractWebExceptions {
    private static final String message = "No Results found in for Incident number";

    public InvalidIncidentNumberException(String incidentNumber) {
        super(message, HttpStatus.NOT_FOUND,
                message + " : " + incidentNumber);
    }
}
