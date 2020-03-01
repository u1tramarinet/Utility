package com.example.utility.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utility.ConditionsUtil;

public class SharedPreferencesWrapper {

    public interface Listener {
        void onChanged(@Nullable SharedPreferences sharedPreferences, @Nullable PreferencesKey key);
    }

    @NonNull
    private SharedPreferences preferences;
    @Nullable
    private SharedPreferences.Editor editor;

    public SharedPreferencesWrapper(@NonNull Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void writeBoolean(@NonNull PreferencesKey key, boolean value) {
        putBoolean(key, value).writeAsync();
    }

    public SharedPreferencesWrapper putBoolean(@NonNull PreferencesKey key, boolean value) {
        requireSameType(key, PreferencesKey.Type.BOOLEAN);
        getEditor().putBoolean(key.name(), value);
        return this;
    }

    public boolean readBoolean(@NonNull PreferencesKey key) {
        requireSameType(key, PreferencesKey.Type.BOOLEAN);
        return preferences.getBoolean(key.name(), (Boolean) key.defaultValue);
    }

    public void writeFloat(@NonNull PreferencesKey key, float value) {
        putFloat(key, value).writeAsync();
    }

    public SharedPreferencesWrapper putFloat(@NonNull PreferencesKey key, float value) {
        requireSameType(key, PreferencesKey.Type.FLOAT);
        getEditor().putFloat(key.name(), value);
        return this;
    }

    public float readFloat(@NonNull PreferencesKey key) {
        requireSameType(key, PreferencesKey.Type.FLOAT);
        return preferences.getFloat(key.name(), (Float) key.defaultValue);
    }

    public void writeInt(@NonNull PreferencesKey key, int value) {
        putInt(key, value).writeAsync();
    }

    public SharedPreferencesWrapper putInt(@NonNull PreferencesKey key, int value) {
        requireSameType(key, PreferencesKey.Type.INT);
        getEditor().putInt(key.name(), value);
        return this;
    }

    public int readInt(@NonNull PreferencesKey key) {
        requireSameType(key, PreferencesKey.Type.INT);
        return preferences.getInt(key.name(), (Integer) key.defaultValue);
    }

    public void writeLong(@NonNull PreferencesKey key, long value) {
        putLong(key, value).writeAsync();
    }

    public SharedPreferencesWrapper putLong(@NonNull PreferencesKey key, long value) {
        requireSameType(key, PreferencesKey.Type.LONG);
        getEditor().putLong(key.name(), value);
        return this;
    }

    public long readLong(@NonNull PreferencesKey key) {
        requireSameType(key, PreferencesKey.Type.LONG);
        return preferences.getLong(key.name(), (Long) key.defaultValue);
    }

    public void writeString(@NonNull PreferencesKey key, @Nullable String value) {
        putString(key, value).writeAsync();
    }

    public SharedPreferencesWrapper putString(@NonNull PreferencesKey key, @Nullable String value) {
        requireSameType(key, PreferencesKey.Type.STRING);
        if (value == null) {
            value = (String) key.defaultValue;
        }
        getEditor().putString(key.name(), value);
        return this;
    }

    public String readString(@NonNull PreferencesKey key) {
        requireSameType(key, PreferencesKey.Type.STRING);
        return preferences.getString(key.name(), (String) key.defaultValue);
    }

    public boolean writeSync() {
        if (editor == null) {
            return false;
        }
        boolean result = editor.commit();
        editor = null;
        return result;
    }

    public void writeAsync() {
        if (editor == null) {
            return;
        }
        editor.apply();
        editor = null;
    }

    public SharedPreferencesWrapper remove(@NonNull PreferencesKey key) {
        getEditor().remove(key.name());
        return this;
    }

    public SharedPreferencesWrapper clear() {
        getEditor().clear();
        return this;
    }

    public boolean contains(@NonNull PreferencesKey key) {
        return preferences.contains(key.name());
    }

    public void registerListener(@Nullable Listener listener) {
        preferences.registerOnSharedPreferenceChangeListener(convertListener(listener));
    }

    public void unregisterListener(@Nullable Listener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(convertListener(listener));
    }

    @NonNull
    private SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor;
    }

    private void requireSameType(@NonNull PreferencesKey key, @NonNull PreferencesKey.Type type) {
        ConditionsUtil.require(key.equalsType(type));
    }

    private SharedPreferences.OnSharedPreferenceChangeListener convertListener(@Nullable final Listener listener) {
        if (listener == null) {
            return null;
        }

        return new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                listener.onChanged(sharedPreferences, PreferencesKey.valueOf(key));
            }
        };
    }
}
