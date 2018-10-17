package com.siziksu.layers.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DatesUtils {

    private DatesUtils() {}

    /**
     * Gets the current time in long format.
     *
     * @return long value
     */
    public static long getCurrentTimeLong() {
        return new Date().getTime();
    }

    /**
     * Formats milliseconds date in the format 01/01/1900 00:00:00.
     *
     * @return string formatted
     */
    public static String getDateString(Long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }
}
