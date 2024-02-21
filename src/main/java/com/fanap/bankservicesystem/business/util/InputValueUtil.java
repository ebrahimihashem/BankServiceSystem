package com.fanap.bankservicesystem.business.util;

import com.fanap.bankservicesystem.business.exception.ExceptionMessageCodes;

public class InputValueUtil {

    public static void checkStringsHaveValue(String[] inputs) {
        for (String input : inputs)
            if (input == null || input.isEmpty())
                throw new IllegalArgumentException(ExceptionMessageCodes.BSS_EMPTY_STRING);
    }

    public static double getNonNegativeValue(Double value) {
        if (value == null)
            return 0;
        else if (value >= 0)
            return value;
        else
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_INPUT_VALUE);
    }

}
