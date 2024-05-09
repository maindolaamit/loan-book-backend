package org.hayo.finance.loanbook.models.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumsTest {
    @Test
    @DisplayName("Test ApprovalStatus")
    void test1() {
        assertEquals(ApprovalStatus.PENDING, ApprovalStatus.valueOf("PENDING"));
        assertEquals(ApprovalStatus.APPROVED, ApprovalStatus.valueOf("APPROVED"));
        assertEquals(ApprovalStatus.REJECTED, ApprovalStatus.valueOf("REJECTED"));

        assertEquals(ApprovalStatus.PENDING, ApprovalStatus.fromValue("Pending"));
    }

    @Test
    @DisplayName("Test PaymentStatus")
    void test2() {
        assertEquals(PaymentStatus.PENDING, PaymentStatus.valueOf("PENDING"));
        assertEquals(PaymentStatus.PARTIAL, PaymentStatus.valueOf("PARTIAL"));
        assertEquals(PaymentStatus.PAID, PaymentStatus.valueOf("PAID"));

        assertEquals("Pending", PaymentStatus.PENDING.getValue());
    }

    @Test
    @DisplayName("Test Payment Frequency")
    void test3() {
        assertEquals(PaymentFrequency.WEEKLY, PaymentFrequency.valueOf("WEEKLY"));
        assertEquals(PaymentFrequency.MONTHLY, PaymentFrequency.valueOf("MONTHLY"));
        assertEquals(PaymentFrequency.ANNUALLY, PaymentFrequency.valueOf("ANNUALLY"));
    }
}