package org.hayo.finance.loanbook.dto.request;

public record UserRegistrationRequest(
        String username,
        String password,
        String email
) {
}
