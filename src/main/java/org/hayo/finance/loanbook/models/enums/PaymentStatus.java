package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus implements ValueEnum {
    PENDING("Pending"),
    PAID("Paid"),
    PARTIAL("Partial");

    private final String value;
}
