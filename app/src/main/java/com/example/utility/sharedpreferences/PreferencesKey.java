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
        final Class<?> clazz;
        @NonNull
        final Object defaultValue;

        Type(@NonNull Class<?> clazz, @NonNull Object defaultValue) {
            ConditionsUtil.require(isSameClass(clazz, defaultValue));

            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }

        private boolean isSameClass(@NonNull Class<?> clazz, @NonNull Object defaultValue) {
            return clazz.isInstance(defaultValue);
        }
    }

    @NonNull
    final Type type;
    @NonNull
    final Object defaultValue;

    PreferencesKey(@NonNull Type type, @Nullable Object defaultValue) {
        if (defaultValue == null) defaultValue = type.defaultValue;
        ConditionsUtil.require(type.isSameClass(type.clazz, defaultValue));

        this.type = type;
        this.defaultValue = defaultValue;
    }

    PreferencesKey(@NonNull Type type) {
        this(type, null);
    }

    boolean isSameType(@NonNull Type type) {
        return this.type == type;
    }
}
