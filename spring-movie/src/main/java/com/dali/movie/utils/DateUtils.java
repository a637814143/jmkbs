package com.dali.movie.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils  {
    public static String dateFormat(Date date){
        //2024-04-24 21:18
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
