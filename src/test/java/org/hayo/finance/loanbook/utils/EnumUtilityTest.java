package org.hayo.finance.loanbook.utils;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumUtilityTest {

    @Test
    @DisplayName("Test getInvalidValueErrorMessage")
    void getInvalidValueErrorMessage() {
        var weekly = EnumUtility.getInvalidValueErrorMessage(PaymentFrequency.class, "test");
        assertEquals("Invalid PaymentFrequency: test. Valid values are: WEEKLY,MONTHLY,ANNUALLY", weekly);
    }

    @Test
    @DisplayName("Test printEnumValues")
    void printEnumValues() {
        var weekly = EnumUtility.printEnumValues(PaymentFrequency.class);
        assertEquals("WEEKLY,MONTHLY,ANNUALLY", weekly);
    }

    @Test
    @DisplayName("Test fromValue")
    void fromValue() {
        val weekly = EnumUtility.fromValue(PaymentFrequency.class, "WEEKLY");
        assertEquals(PaymentFrequency.WEEKLY, weekly);

        val monthly = EnumUtility.fromValue(PaymentFrequency.class, "MONTHLY");
        assertEquals(PaymentFrequency.MONTHLY, monthly);

        val yearly = EnumUtility.fromValue(PaymentFrequency.class, "ANNUALLY");
        assertEquals(PaymentFrequency.ANNUALLY, yearly);

        String values = EnumUtility.printEnumValues(PaymentFrequency.class);
        try {
            EnumUtility.fromValue(PaymentFrequency.class, "INVALID");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid PaymentFrequency: INVALID. Valid values are: " + values, e.getMessage());
        }
    }
}