package com.afolayan.alc.cryptocompare.helper;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class AppHelper {

    public static final String CRYPTO_ID = "crypto_id";

    public static String getDate( Date dateUpdated ) {
        Date now = Calendar.getInstance().getTime();
        long dateDiff = now.getTime() - dateUpdated.getTime();

        long days = TimeUnit.MILLISECONDS.toDays(dateDiff);
        long hours = TimeUnit.MILLISECONDS.toHours(dateDiff) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(dateDiff) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(dateDiff) % 60;

        StringBuilder buffer = new StringBuilder();
        if( days != 0 ){
            if( days == 1) buffer.append(" 1 day ");
            if( days > 1 ) buffer.append(days).append(" days ");
        }
        if( hours != 0 ){
            if( hours == 1) buffer.append("1 hour ");
            if( hours > 1 ) buffer.append(hours).append(" hours ");
        }
        if( minutes != 0 ){
            if( minutes == 1) buffer.append("1 min ");
            if( minutes > 1 ) buffer.append(minutes).append(" mins ");
        }
        if( seconds != 0 ){
            if( seconds == 1) buffer.append("1 second");
            if( seconds > 1 ) buffer.append(seconds).append(" seconds ");
        }

        buffer.append("ago");
        return buffer.toString();
    }

}
