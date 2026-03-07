package com.paulzzh.yuzu.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("unused")
public final class JsonParseUtils {
    @Nonnull
    public static JsonObject tryGetAsJsonObject(JsonObject jsonObject, String name) throws JsonParseException {
        return tryGetAsJsonObject(jsonObject, name, "");
    }

    @Nonnull
    public static JsonObject tryGetAsJsonObject(JsonObject jsonObject, String name, String prefix) throws JsonParseException {
        if (!jsonObject.has(name)) throw new JsonParseException("'" + prefix + name + "' is must but missing!");
        return jsonObject.getAsJsonObject(name);
    }

    @Nonnull
    public static JsonArray tryGetAsJsonArray(JsonObject jsonObject, String name) throws JsonParseException {
        return tryGetAsJsonArray(jsonObject, name, "");
    }

    @Nonnull
    public static JsonArray tryGetAsJsonArray(JsonObject jsonObject, String name, String prefix) throws JsonParseException {
        if (!jsonObject.has(name)) throw new JsonParseException("'" + prefix + name + "' is must but missing!");
        return jsonObject.getAsJsonArray(name);
    }

    @Nonnull
    public static JsonElement tryGet(JsonObject jsonObject, String name) throws JsonParseException {
        return tryGet(jsonObject, name, "");
    }

    @Nonnull
    public static JsonElement tryGet(JsonObject jsonObject, String name, String prefix) throws JsonParseException {
        if (!jsonObject.has(name)) throw new JsonParseException("'" + prefix + name + "' is must but missing!");
        return jsonObject.get(name);
    }

    @Nullable
    public static JsonObject mergeVariant(JsonObject fallback, @Nullable JsonObject current) {
        if (current == null) {
            return fallback;
        }
        JsonObject merged = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : fallback.entrySet()) {
            merged.add(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, JsonElement> entry : current.entrySet()) {
            merged.add(entry.getKey(), entry.getValue());
        }
        return merged;
    }
}
