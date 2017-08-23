package com.application.robat.postage;

/**
 * Created by domin on 23.08.2017.
 */
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Timeconversion
{
    DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");

    long unixtime;
    public long timeConversion(String time)
    {
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));//Specify your timezone
        try
        {
            unixtime = dfm.parse(time).getTime();
            unixtime=unixtime/1000;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return unixtime;
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("MM/yyyy/dd HH:mm:ss");
        return format.format(date);
    }

}
