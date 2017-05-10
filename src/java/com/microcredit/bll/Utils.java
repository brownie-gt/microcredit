package com.microcredit.bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Date parsearFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(fecha);
        try {
            return sdf.parse(strDate);
        } catch (ParseException ex) {
            logger.error("", ex);
            return null;
        }
    }

    public static Date addDaysToDate(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        // add the working days
        int workingDaysToAdd = 23;
        for (int i = 0; i < workingDaysToAdd; i++) {
            do {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            } while (!isWorkingDay(cal));
        }
        return cal.getTime();
    }

    private static boolean isWorkingDay(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            return false;
        }
        // tests for other holidays here
        // ...
        return true;
    }

}
