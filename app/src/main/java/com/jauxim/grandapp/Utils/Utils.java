package com.jauxim.grandapp.Utils;

public class Utils {

    public static String getPriceFormated(Long eurocents){
        return String.format("%.2f€", eurocents);
    }
}
