package org.hayo.finance.loanbook.utils;

import lombok.val;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumUtility {
    public static <E extends Enum<E>> String getInvalidValueErrorMessage(Class<E> valueEnum, String value) {
        String sb = String.format("Invalid %s: ", valueEnum.getSimpleName()) + value + ". Valid values are: " +
                printEnumValues(valueEnum);
        return sb;
    }

    public static <E extends Enum<E>> String printEnumValues(Class<E> enumClass) {
        StringBuilder sb = new StringBuilder();
        val collect = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).collect(Collectors.joining(","));
        return sb.append(collect).toString();
    }

    public static <E extends Enum<E>> E fromValue(Class<E> enumClass, String value) {
        for (E enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException(getInvalidValueErrorMessage(enumClass, value));
    }
}
