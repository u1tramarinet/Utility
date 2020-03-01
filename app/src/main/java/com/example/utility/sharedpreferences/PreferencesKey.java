package com.example.utility.sharedpreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utility.ConditionsUtil;

public enum PreferencesKey {
    ;
    enum Type {
        BOOLEAN(Boolean.class, true),
        FLOAT(Float.class, 0F),
        INT(Integer.class, 0),
        LONG(Long.class, 0L),
        STRING(String.class, "");

        @NonNull
        private final Class<?> clazz;
        @NonNull
        private final Object defaultValue;

        Type(@NonNull Class<?> clazz, @NonNull Object defaultValue) {
            ConditionsUtil.require(isClassSame(clazz, defaultValue));

            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }

        private static boolean isClassSame(@NonNull Class<?> clazz, @NonNull Object defaultValue) {
            return clazz.isInstance(defaultValue);
        }
    }

    @NonNull
    public final Type type;
    @NonNull
    public final Object defaultValue;

    PreferencesKey(@NonNull Type type, @Nullable Object defaultValue) {
        if (defaultValue == null) defaultValue = type.defaultValue;
        ConditionsUtil.require(Type.isClassSame(type.clazz, defaultValue));

        this.type = type;
        this.defaultValue = defaultValue;
    }

    PreferencesKey(@NonNull Type type) {
        this(type, null);
    }

    public boolean equalsType(@NonNull Type type) {
        return this.type == type;
    }
}
