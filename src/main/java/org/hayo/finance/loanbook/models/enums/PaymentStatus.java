package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hayo.finance.loanbook.utils.EnumUtility;

@AllArgsConstructor
@Getter
public enum PaymentStatus implements ValueEnum {
    PENDING("Pending"),
    PAID("Paid");

    private final String value;

    public static PaymentStatus fromValue(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue().equals(value)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("Invalid Application Status : " + value);
    }
}
