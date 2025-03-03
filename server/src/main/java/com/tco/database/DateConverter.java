package com.tco.database;

import java.time.*;

public class DateConverter 
{

    public static String LocalDateTimeToSQLString(LocalDateTime date)
    {
        int year = date.getYear(); 
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();

        //'2024-10-28 15:37:40'
        return "'" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "'";
    }
    
}
