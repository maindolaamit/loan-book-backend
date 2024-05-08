package org.hayo.finance.loanbook.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class AbstractWebExceptions extends RuntimeException implements WebExceptions {

    private final String message;
    private final HttpStatus status;
    private final String reason;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

}

