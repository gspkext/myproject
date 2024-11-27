package com.example.transaction.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
} 