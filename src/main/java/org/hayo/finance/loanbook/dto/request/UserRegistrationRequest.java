package org.hayo.finance.loanbook.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRegistrationRequest(
        @NotNull(message = "First name cannot be null")
        String firstName,
        String lastName,
        @NotNull(message = "Password cannot be null")
        String password,
        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        String email
) {
}
