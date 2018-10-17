package com.siziksu.layers.common.utils;

import java.util.List;

public final class StringUtils {

    private StringUtils() {}

    public static String compoundStringFromStringList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String string : list) {
            builder.append(string).append("\n");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().replace("\n", "\n\n");
    }

}
