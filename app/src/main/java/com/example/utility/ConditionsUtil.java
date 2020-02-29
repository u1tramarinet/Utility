package com.example.utility;

import androidx.annotation.Nullable;

public final class ConditionsUtil {

    private ConditionsUtil() {
    }

    public static void require(boolean value) {
        require(value, null);
    }

    public static void require(boolean value, @Nullable String message) {
        if (value) return;
        throw new IllegalArgumentException(message);
    }
}
