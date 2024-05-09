package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hayo.finance.loanbook.utils.EnumUtility;

@AllArgsConstructor
@Getter
public enum PaymentStatus implements ValueEnum {
    PENDING("Pending"),
    REJECTED("Rejected"),
    PAID("Paid");

    private final String value;
}
