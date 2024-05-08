package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus implements ValueEnum {
    DRAFT("Draft"),
    PENDING("Published"),
    APPROVED("Approved"),
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

    private static String getInvalidValueErrorMessage(String value) {
        StringBuilder sb = new StringBuilder("Invalid PageTypes optionValue: ");
        sb.append(value).append(". Valid values are: ");
        for (PaymentFrequency paymentFrequency : PaymentFrequency.values()) {
            sb.append(paymentFrequency.getValue()).append(", ");
        }
        return sb.toString();
    }
}
