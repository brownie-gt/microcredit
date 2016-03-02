package com.microcredit.bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

}
