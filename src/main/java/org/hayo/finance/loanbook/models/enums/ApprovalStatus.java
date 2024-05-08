package org.hayo.finance.loanbook.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApprovalStatus implements ValueEnum {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    public static ApprovalStatus fromValue(String value) {
        for (ApprovalStatus status : ApprovalStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status : " + value);
    }
}
