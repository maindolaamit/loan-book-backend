package org.hayo.finance.loanbook.utils;

public class EnumUtility {
    public static <E extends Enum<E>> String getInvalidValueErrorMessage(Class<E> valueEnum, String value) {
        String sb = String.format("Invalid %s: ", valueEnum.getName()) + valueEnum.getName() + ". Valid values are: " +
                printEnumValues(valueEnum);
        return sb;
    }

    public static <E extends Enum<E>> String printEnumValues(Class<E> enumClass) {
        StringBuilder sb = new StringBuilder();
        for (E enumValue : enumClass.getEnumConstants()) {
            sb.append(enumValue.name());
        }
        return sb.toString();
    }
}
