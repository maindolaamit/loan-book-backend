package org.hayo.finance.loanbook.utils;

import lombok.val;
import org.hayo.finance.loanbook.models.enums.PaymentFrequency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanUtilityTest {

    @Test
    @DisplayName("Test getNextDueDate")
    void getNextDueDate() {
        val applicationDate = LocalDateTime.now();
        val monthlyDueDate = LoanUtility.getNextDueDate(applicationDate, 1, PaymentFrequency.MONTHLY);
        assertEquals(applicationDate.plusMonths(1), monthlyDueDate);

        val annuallyDueDate = LoanUtility.getNextDueDate(applicationDate, 1, PaymentFrequency.ANNUALLY);
        assertEquals(applicationDate.plusYears(1), annuallyDueDate);

        val weeklyDueDate = LoanUtility.getNextDueDate(applicationDate, 1, PaymentFrequency.WEEKLY);
        assertEquals(applicationDate.plusWeeks(1), weeklyDueDate);

        assertEquals(applicationDate.plusWeeks(4), LoanUtility.getNextDueDate(applicationDate, 4));
    }

    @Test
    @DisplayName("Test getInstallmentPerTerm for int value")
    void getInstallmentPerTerm() {
        val payments = LoanUtility.getInstallmentPerTerm(1000, 4);
        assertEquals(4, payments.size());
        assertEquals(250, payments.get(0));
        assertEquals(250, payments.get(1));
        assertEquals(250, payments.get(2));
        assertEquals(250, payments.get(3));
    }

    @Test
    @DisplayName("Test getInstallmentPerTerm for double value")
    void getInstallmentPerTermDouble() {
        val payments = LoanUtility.getInstallmentPerTerm(1000.0, 4);
        assertEquals(4, payments.size());
        assertEquals(250.0, payments.get(0));
        assertEquals(250.0, payments.get(1));
        assertEquals(250.0, payments.get(2));
        assertEquals(250.0, payments.get(3));
    }

    @Test
    @DisplayName("Test getInstallmentPerTerm for double value with decimal")
    void getInstallmentPerTermDoubleWithDecimal() {
        val payments = LoanUtility.getInstallmentPerTerm(1000.0, 3);
        assertEquals(3, payments.size());
        assertEquals(333.33, payments.get(0));
        assertEquals(333.33, payments.get(1));
        assertEquals(333.34, payments.get(2));

        val payments2 = LoanUtility.getInstallmentPerTerm(1807.0, 7);
        assertEquals(7, payments2.size());
        assertEquals(258.14, payments2.get(0));
        assertEquals(258.14, payments2.get(1));
        assertEquals(258.14, payments2.get(2));
        assertEquals(258.14, payments2.get(3));
        assertEquals(258.14, payments2.get(4));
        assertEquals(258.14, payments2.get(5));
        assertEquals(258.16, payments2.get(6));
    }

    @Test
    @DisplayName("Test isNullOrEmpty")
    void isNullOrEmpty() {
        assertTrue(LoanUtility.isNullOrEmpty(null));
        assertTrue(LoanUtility.isNullOrEmpty(""));
        assertTrue(LoanUtility.isNullOrEmpty(" "));
        assertFalse(LoanUtility.isNullOrEmpty("test"));
    }

    @Test
    @DisplayName("Test getStringDate")
    void getStringDate() {
        val date = LocalDateTime.of(2021, 10, 10, 10, 10);
        assertEquals("10/10/2021", LoanUtility.getStringDate(date));
    }
}