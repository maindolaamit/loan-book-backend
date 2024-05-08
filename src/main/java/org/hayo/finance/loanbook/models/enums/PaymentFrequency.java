package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentFrequency implements ValueEnum {
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private final String value;

    public static PaymentFrequency fromValue(String value) throws IllegalArgumentException {
        for (PaymentFrequency paymentFre : PaymentFrequency.values()) {
            if (paymentFre.getValue().equals(value)) {
                return paymentFre;
            }
        }
        throw new IllegalArgumentException(getInvalidValueErrorMessage(value));
    }

    private static String getInvalidValueErrorMessage(String value) {
        StringBuilder sb = new StringBuilder("Invalid Payment Frequency: ");
        sb.append(value).append(". Valid values are: ");
        for (PaymentFrequency paymentFrequency : PaymentFrequency.values()) {
            sb.append(paymentFrequency.getValue()).append(", ");
        }
        return sb.toString();
    }
}
