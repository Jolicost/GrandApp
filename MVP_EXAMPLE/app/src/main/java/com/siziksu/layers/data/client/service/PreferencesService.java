package com.siziksu.layers.data.client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.siziksu.layers.common.function.Consumer;

import javax.inject.Inject;

public final class PreferencesService {

    private final Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Inject
    public PreferencesService(Context context) {
        this.context = context;
    }

    public PreferencesService useDefaultSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        createEditor();
        return this;
    }

    public PreferencesService usePreferences(String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        createEditor();
        return this;
    }

    public String getValue(String key, String defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getString(key, defaultValue);
    }

    public int getValue(String key, int defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getInt(key, defaultValue);
    }

    public long getValue(String key, long defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getLong(key, defaultValue);
    }

    public float getValue(String key, float defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getFloat(key, defaultValue);
    }

    public boolean getValue(String key, boolean defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getBoolean(key, defaultValue);
    }

    public void applyValue(String key, String value) {
        doIfEditorNotNull(() -> editor.putString(key, value).apply());
    }

    public void applyValue(String key, int value) {
        doIfEditorNotNull(() -> editor.putInt(key, value).apply());
    }

    public void applyValue(String key, long value) {
        doIfEditorNotNull(() -> editor.putLong(key, value).apply());
    }

    public void applyValue(String key, float value) {
        doIfEditorNotNull(() -> editor.putFloat(key, value).apply());
    }

    public void applyValue(String key, boolean value) {
        doIfEditorNotNull(() -> editor.putBoolean(key, value).apply());
    }

    public PreferencesService setValue(String key, String value) {
        doIfEditorNotNull(() -> editor.putString(key, value));
        return this;
    }

    public PreferencesService setValue(String key, int value) {
        doIfEditorNotNull(() -> editor.putInt(key, value));
        return this;
    }

    public PreferencesService setValue(String key, long value) {
        doIfEditorNotNull(() -> editor.putLong(key, value));
        return this;
    }

    public PreferencesService setValue(String key, float value) {
        doIfEditorNotNull(() -> editor.putFloat(key, value));
        return this;
    }

    public PreferencesService setValue(String key, boolean value) {
        doIfEditorNotNull(() -> editor.putBoolean(key, value));
        return this;
    }

    public PreferencesService deleteKey(String key) {
        doIfEditorNotNull(() -> editor.remove(key));
        return this;
    }

    public void apply() {
        doIfEditorNotNull(() -> editor.apply());
    }

    private void createEditor() {
        editor = preferences.edit();
    }

    private void doIfEditorNotNull(Consumer consumer) {
        if (preferences != null && editor != null) {
            consumer.consume();
        }
    }
}
