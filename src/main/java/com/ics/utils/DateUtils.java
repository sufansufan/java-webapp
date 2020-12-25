package com.ics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 */
public class DateUtils {

	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * string转date
	 * @param dateStr
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date StringToDate(String dateStr, String pattern){
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			date = df.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * date转string
	 * @param date
	 * @param pattern 日期格式
	 * @return
	 */
	public static String DateToString(Date date, String pattern){
		String dateStr = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			dateStr = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * date转Calendar
	 * @param date
	 * @return
	 */
	public static Calendar DateToCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 设置日期为当天的最小时间
	 * @param date
	 * @return
	 */
	public static Date setMinTimeForDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 设置日期为当天的最大时间
	 * @param date
	 * @return
	 */
	public static Date setMaxTimeForDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 998);
		return cal.getTime();
	}

	/**
	 * 设置日期为当月的最小时间
	 * @param date
	 * @return
	 */
	public static Date setMinTimeForMonthDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 设置日期为当月最大时间
	 * @param date
	 * @return
	 */
	public static Date setMaxTimeForMonthDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 998);
		return cal.getTime();
	}

	/**
	 * 设置日期为当年的最小时间
	 * @param date
	 * @return
	 */
	public static Date setMinTimeForYearDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 设置日期为当年最大时间
	 * @param date
	 * @return
	 */
	public static Date setMaxTimeForYearDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 998);
		return cal.getTime();
	}

	/**
	 * 判断两时间是否相等
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEquals(Date date1,Date date2){
		boolean flag = false;
		if(DateToCalendar(date1).equals(DateToCalendar(date2))){
			flag = true;
		}
		return flag;
	};

	/**
	 * 判断date1是否在date2之后(date1>date2)
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isMoreThan(Date date1,Date date2){
		boolean flag = false;
		if(DateToCalendar(date1).after(DateToCalendar(date2))){
			flag = true;
		}
		return flag;
	};

	/**
	 * 判断date1是否在date2之前(date1<date2)
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isLessThan(Date date1,Date date2){
		boolean flag = false;
		if(DateToCalendar(date1).before(DateToCalendar(date2))){
			flag = true;
		}
		return flag;
	};

	/**
	 * 判断两时间是否在同一小时
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameHour(Date date1,Date date2){
		String pattern = "yyyyMMddHH";
		return DateToString(date1,pattern).equals(DateToString(date2,pattern));
	};

	/**
	 * 判断两时间是否在同日
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1,Date date2){
		String pattern = "yyyyMMdd";
		return DateToString(date1,pattern).equals(DateToString(date2,pattern));
	};

	/**
	 * 判断两时间是否在同月
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameMonth(Date date1,Date date2){
		String pattern = "yyyyMM";
		return DateToString(date1,pattern).equals(DateToString(date2,pattern));
	};

	/**
	 * 判断两时间是否在同年
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameYear(Date date1,Date date2){
		String pattern = "yyyy";
		return DateToString(date1,pattern).equals(DateToString(date2,pattern));
	};

	/**
	 * 判断两时间是否在1小时内(含1小时)
	 * @param beginDate
	 * @param endDate
	 *  2014/9/12 15:35:55 , 2014/9/12 16:30:45, return true
	 * @return
	 */
	public static boolean in1Hours(Date beginDate,Date endDate){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(beginDate);
		Calendar cal2 = DateToCalendar(endDate);
		cal1.add(Calendar.HOUR_OF_DAY, 1);
		if(cal1.after(cal2) || cal1.equals(cal2)){
			flag = true;
		}
		return flag;
	};
	/**
	 * 判断两时间是否在同一个月内(含)
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean in1Month(Date beginDate,Date endDate){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(beginDate);
		Calendar cal2 = DateToCalendar(endDate);
		cal1.add(Calendar.MONTH, 1);
		if(cal1.after(cal2) || cal1.equals(cal2)){
			flag = true;
		}
		return flag;
	};
	/**
	 * 判断两时间是否在同一年(含)
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean in1Year(Date beginDate,Date endDate){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(beginDate);
		Calendar cal2 = DateToCalendar(endDate);
		cal1.add(Calendar.YEAR, 1);
		if(cal1.after(cal2) || cal1.equals(cal2)){
			flag = true;
		}
		return flag;
	};
	/**
	 * 判断两时间是否在24小时内(含24小时)
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean in24Hours(Date beginDate,Date endDate){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(beginDate);
		Calendar cal2 = DateToCalendar(endDate);
		cal1.add(Calendar.DATE, 1);
		if(cal1.after(cal2) || cal1.equals(cal2)){
			flag = true;
		}
		return flag;
	};

	/**
	 * 判断时间是否为整点(分钟为0)
	 * @param date
	 * @return
	 */
	public static boolean isOnTheHour(Date date){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(date);
		if(cal1.get(Calendar.MINUTE)==0 && cal1.get(Calendar.SECOND)==0){
			flag = true;
		}
		return flag;
	};
	/**
	 * 判断时间是否为一天的开始(小时、分钟为0)
	 * @param date
	 * @return
	 */
	public static boolean isOnTheDay(Date date){
		boolean flag = false;
		Calendar cal1 = DateToCalendar(date);
		if(cal1.get(Calendar.HOUR_OF_DAY)==0 && isOnTheHour(date)){
			flag = true;
		}
		return flag;
	};

	/**
	 * 根据参数设置时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

    public static Date addSeconds(Date date, int amount)
    {
        return add(date, 13, amount);
    }

    /**
     * 叠加特定的时间属性
     * @param date
     * @param calendarField
     * @param amount 值
     * @return
     */
    public static Date add(Date date, int calendarField, int amount)
    {
        if(date == null)
        {
            throw new IllegalArgumentException("The date must not be null");
        } else
        {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    /**
     * 设置特定的时间属性
     * @param date
     * @param calendarField
     * @param amount 值
     * @return
     */
    public static Date set(Date date, int calendarField, int value)
    {
        if(date == null)
        {
            throw new IllegalArgumentException("The date must not be null");
        } else
        {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(calendarField, value);
            return c.getTime();
        }
    }

    /*
     *	获取当前时间之前几小时 hour
     */
    public static String getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static boolean inThisDay(Date startTime){
    	 	Calendar calendar = Calendar.getInstance();
	        int now_y = calendar.get(Calendar.YEAR);//得到年份
	        int now_m = calendar.get(Calendar.MONTH)+1;//得到月份
	        int now_d = calendar.get(Calendar.DATE);//得到月份中今天的号数

	        calendar.setTime(startTime);
	        int y = calendar.get(Calendar.YEAR);//得到年份
	        int m = calendar.get(Calendar.MONTH)+1;//得到月份
	        int d = calendar.get(Calendar.DATE);//得到月份中今天的号数

	        if(now_y==y&&now_m==m&&now_d==d) {
	        	return true;
			}else{
			    return false;
			}
    }

    public static boolean isAfterLatestMonth(Date startTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    	Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(format.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, -31);
		Date before1month = calendar.getTime();

		if(before1month.getTime() < startTime.getTime()){
			return true;
		}else{
			return false;
		}
    }

    public static boolean isAfterLatestWeek(Date startTime){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    	Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(format.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		Date before1w = calendar.getTime();

		if(before1w.getTime() < startTime.getTime()){
			return true;
		}else{
			return false;
		}
    }

	/**
	 * 当前季度的开始时间
	 *
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间
	 *
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

    public static void main(String[] args) {
    	long milliSecond = 1583918369984L;
        Date date = new Date();
        date.setTime(milliSecond);
    	System.out.println(DateToString(date,"yyyy:MM:dd hh:mm:ss"));
    	System.out.println(new Date().getTime());
	}
}
