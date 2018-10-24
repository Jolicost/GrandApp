package com.jauxim.grandapp.Utils;


public class Utils {

    public static String getPriceFormated(Long eurocents){
        return String.format("%.2l€", eurocents);
    }

    public static String getTimeFormated(String time){
        //TODO: TBI
        return "";
    }

    public static String getCountDownTime(Long millis){
        Long diff = System.currentTimeMillis() - millis;

        if (diff<0)
            return "GONE!";

        Long min = diff/(60*1000);
        if (min<60)
            return min+"min";

        Long hours = min/60;
        if (hours<24)
            return hours+"hours";

        Long days = hours/24;
        if (days<7)
            return days+"days";

        return days/7+"weeks";
    }
}
