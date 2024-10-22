package com.esproject.demo.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class ConvertorUtil {
    private static final Gson gson = new Gson();

    public static <K, T> T convertToObject(K initialObject, Class<T> clazz) {
        String res = gson.toJson(initialObject);
        return gson.fromJson(res, clazz);
    }

    public static <K, T> List<T> convertToObjectList(List<K> initialObject) {
        String res = gson.toJson(initialObject);

        Type resultListType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(res, resultListType);
    }
}
