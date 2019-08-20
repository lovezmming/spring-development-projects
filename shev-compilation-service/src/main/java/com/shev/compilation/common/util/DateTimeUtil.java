package com.shev.compilation.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil
{
    public static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
    
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    private static DateFormat getDateFormat()
    {
        return new SimpleDateFormat(defaultDateFormat);
    }

    public static String formatDate(long time)
    {
        return getDateFormat().format(getDate(time));
    }

    public static String formatDate(Date date)
    {
        if (date == null)
            return null;
        return getDateFormat().format(date);
    }

    public static String formatDate(Date date, String formatString)
    {
        if (date == null)
            return null;
        
        DateFormat df = null;
        try
        {
            df = new SimpleDateFormat(formatString);
        }catch(IllegalArgumentException exception)
        {
            df = getDateFormat();
        }
        
        return df.format(date);
    }

    public static Date parseDate(String dateStr)
    {
        return parseDate(dateStr, defaultDateFormat, Locale.getDefault());
    }

    public static Date parseDate(String dateStr, String formatString)
    {
        return parseDate(dateStr, formatString, Locale.getDefault());
    }

    public static Date parseDate(String dateStr, String formatString, Locale locale)
    {
        if (dateStr == null)
            return null;
        
        DateFormat df = null;
        try
        {
            df = new SimpleDateFormat(formatString, locale);
        }
        catch(IllegalArgumentException exception)
        {
            df = getDateFormat();
        }
        
        try
        {
            return df.parse(dateStr);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    public static Date getDate(long time)
    {
        return new Date(time);
    }

    public static Date zeroTime(Date date)
    {
        if (date == null)
            return null;
        
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(date);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DATE));
        return cal.getTime();
    }

    public static Date getDateThru(Date dateFrom, int field, int amount)
    {
        if (Calendar.HOUR == field || Calendar.HOUR_OF_DAY == field)
        {
            if (amount % 24 == 0)
                return DateTimeUtil.dateAdd(dateFrom, Calendar.DATE, amount/24);
        }
        
        return DateTimeUtil.dateAdd(dateFrom, field, amount);
    }

    public static Date dateAdd(Date dateFrom, int field, int amount)
    {
        if (amount == 0)
            return dateFrom;

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static long getTimeMillis(String time) 
    {  
        return getTodayTime(time).getTime();
    }  
    
    public static Date getTodayTime(String time)
    {
        if (TextUtil.isEmpty(time))
            return null;
        try 
        {  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");  
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);  
            return curDate;  
        } 
        catch (ParseException e) 
        {  
            throw new RuntimeException(e);
        }
    }

    public static Date getDateTime(String dateTime)
    {
        try 
        {  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateTime);
        } 
        catch (ParseException e) 
        {  
            throw new RuntimeException(e);
        }
    }

    public static Date getDate(int month, int day)
    {
        Calendar current = Calendar.getInstance();
        current.set(Calendar.MONTH, month);
        current.set(Calendar.DAY_OF_MONTH, day);
        return current.getTime();
    }

    public static Date getLastYearDate(int month, int day)
    {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.YEAR, -1);
        current.set(Calendar.MONTH, month);
        current.set(Calendar.DAY_OF_MONTH, day);
        return current.getTime();
    }

    public static Date getFirstDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date parseDateCalDate(String dateStr)
    {
        if (TextUtil.isEmpty(dateStr))
            return null;
        
        dateStr = dateStr.replace('/', '-');
        try
        {
            int pos = dateStr.indexOf('-');
            if (pos == -1)
                return null;
            
            String firstSeg = dateStr.substring(0, pos);
            Date retDate = null;
            if (firstSeg.length() == 4)
            {
                retDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            }
            else if (firstSeg.length() <= 2)
            {
                retDate = new SimpleDateFormat("MM-dd-yyyy").parse(dateStr);
            }
            
            return retDate;
        }
        catch(Exception e)
        {
            return null;
        } 
    }
    
    public static int diffWeeks(Date from, Date to)
    {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(from);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        return Math.abs(fromCalendar.get(Calendar.WEEK_OF_YEAR) - toCalendar.get(Calendar.WEEK_OF_YEAR));
    }
    
    public static int getWeeks(Date date, Date beginDate)
    {
        return timeBettwen(date, beginDate, "WEEK");
    }

    private static int timeBettwen(Date date1, Date date2, String datetype)
    {
        int days = 0;
        int weeks = 0;
        int months = 0;
        Calendar can1 = Calendar.getInstance();
        can1.setFirstDayOfWeek(Calendar.MONDAY);
        can1.setTime(date1);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(date2);
        can2.setFirstDayOfWeek(Calendar.MONDAY);
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);
        Calendar can = null;
        if (can1.before(can2))
        {
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            weeks -= can1.get(Calendar.WEEK_OF_YEAR);
            weeks += can2.get(Calendar.WEEK_OF_YEAR);
            months -= can1.get(Calendar.MONTH);
            months += can2.get(Calendar.MONTH);
            can = can1;
        } else
        {
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            weeks -= can2.get(Calendar.WEEK_OF_YEAR);
            weeks += can1.get(Calendar.WEEK_OF_YEAR);
            months -= can2.get(Calendar.MONTH);
            months += can1.get(Calendar.MONTH);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++)
        {
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            weeks += can.getActualMaximum(Calendar.WEEK_OF_YEAR);
            months += can.getActualMaximum(Calendar.MONTH) + 1;
            can.add(Calendar.YEAR, 1);
        }
        if (datetype.equals("DAY"))
        {
            return days;
        } 
        else if (datetype.equals("WEEK"))
        {
            return weeks;
        } 
        else if (datetype.equals("MONTH"))
        {
            return months;
        }
        return 0;
    }
    public static int dayOfWeek(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static Date getTimesWeekmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return  cal.getTime();
    }

    public  static Date getTimesWeeknight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning(date));
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }
     
    public static String getWeek(Date date){
         SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  
         String week = sdf.format(date);  
         return week;  
    }
     
     public static String getWeek(Date date, Locale locale)
     {
         SimpleDateFormat sdf = new SimpleDateFormat("EEEE",locale);  
         String week = sdf.format(date);  
         return week; 
     }

    public static Date getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

     public static Date getTimesnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }

     public static Date getDateByTime(int hour,int minute,int second) {
         Calendar cal = Calendar.getInstance();
         cal.set(Calendar.HOUR_OF_DAY, hour);
         cal.set(Calendar.MINUTE, minute);
         cal.set(Calendar.SECOND, second);
         cal.set(Calendar.MILLISECOND, 0);
         return  cal.getTime();
     }
}
