package org.hayo.finance.loanbook.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @Email(message = "Email should be valid")
        @NotNull(message = "Email cannot be null")
        String email,
        @NotNull(message = "Password cannot be null")

        @NotBlank(message = "Password cannot be blank")
        String password) {
}
