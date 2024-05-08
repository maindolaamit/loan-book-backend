package org.hayo.finance.loanbook.models.exceptions;

import org.springframework.http.HttpStatus;

public interface WebExceptions {

    String getMessage();

    HttpStatus getStatus();

    String getReason();
}
