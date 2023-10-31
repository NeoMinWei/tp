package seedu.financialplanner.enumerations;

import java.util.Arrays;

public enum IncomeType {
    SALARY, INVESTMENTS, ALLOWANCE, OTHERS;

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
