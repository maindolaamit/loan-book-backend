package org.hayo.finance.loanbook.utils;

import org.hayo.finance.loanbook.models.enums.PaymentFrequency;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanUtility {
    public static LocalDateTime getNextDueDate(LocalDateTime lastDueDate, Integer term) {
        return lastDueDate.plusWeeks(term);
    }

    public static LocalDateTime getNextDueDate(LocalDateTime lastDueDate, Integer term, PaymentFrequency frequency) {
        return switch (frequency) {
            case MONTHLY -> lastDueDate.plusMonths(term);
            case ANNUALLY -> lastDueDate.plusYears(term);
            default -> lastDueDate.plusWeeks(term);
        };
    }


    public static List<Double> getInstallmentPerTerm(double loanAmount, int numOfTerms) {
        DecimalFormat df = new DecimalFormat("#.##");
        double installmentPerTerm = Double.parseDouble(df.format(loanAmount / numOfTerms));
        double totalInstallment = 0;

        List<Double> installments = new ArrayList<>();
        for (int i = 0; i < numOfTerms - 1; i++) {
            installments.add(installmentPerTerm);
            totalInstallment += installmentPerTerm;
        }

        // The last installment includes the remaining amount
        double lastInstallment = Double.parseDouble(df.format(loanAmount - totalInstallment));
        installments.add(lastInstallment);

        return installments;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.isBlank();
    }

    public static String getStringDate(LocalDateTime date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return dateFormat.format(date);
    }
}
