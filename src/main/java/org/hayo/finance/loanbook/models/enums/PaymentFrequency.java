package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentFrequency {
    WEEKLY,
    MONTHLY,
    ANNUALLY;

    private static String getInvalidValueErrorMessage(String value) {
        StringBuilder sb = new StringBuilder("Invalid Payment Frequency: ");
        sb.append(value).append(". Valid values are: ");
        for (PaymentFrequency paymentFrequency : PaymentFrequency.values()) {
            sb.append(paymentFrequency.name()).append(", ");
        }
        return sb.toString();
    }
}
