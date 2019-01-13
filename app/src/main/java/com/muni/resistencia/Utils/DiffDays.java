package com.muni.resistencia.Utils;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiffDays {

    public static int daysDiff(String fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endDateMillis = System.currentTimeMillis();
        long startDateMillis = sdf.getCalendar().getTimeInMillis();
        long differenceMillis = endDateMillis - startDateMillis;
        int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
        Log.i("difere",""+daysDifference);
        return daysDifference;
    }

}
