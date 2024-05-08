package org.hayo.finance.loanbook.dto;

import org.hayo.finance.loanbook.models.enums.PaymentStatus;

import java.time.LocalDate;

public record PaymentSchedule(LocalDate dueDate, Double paymentAmount,
                              Double principal, Double interest, Double balance,
                              PaymentStatus status) {
}
