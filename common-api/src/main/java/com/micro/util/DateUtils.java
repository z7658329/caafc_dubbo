package com.micro.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 默认日期格式
	 */
		public static String DEFAULT_FORMAT = "yyyyMMdd HHmmss zzz";

		public static void main(String[] args) {
            System.out.println(getDate());
				System.out.println(getCurrYearFirst());
				System.out.println(getCurrYearLast());
				System.out.println(getMonthFirst());
				System.out.println(getMonthLast());
				System.out.println(getDayFirst());
				System.out.println(getDayLast());
	    }
	      
	/**
	 * 格式化日期
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatDate(Date date){
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

    /**
     * 获取当前日期字符串
     *
     * @return String 日期字符串
     */

    public static String getDate(){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(new Date());
        return sDate;
    }

	/**
	 * 获取当年的第一天
	 * @param
	 * @return
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * @param
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * @param
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * @param
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		calendar.add(Calendar.DATE,1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 *
	 * 获取当前月第一天日期
	 * @param
	 * @return String
	 *
	 */
	public static Date  getMonthFirst(){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		   return c.getTime();
	}

	/**
	 *
	 * 获取当前月最后天日期
	 * @param
	 * @return String
	 *
	 */
	public static Date  getMonthLast(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DATE,1);
		return c.getTime();
	}


	public static Date  getDayFirst(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date  getDayLast(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DATE,1);
		return c.getTime();
	}
	    
}
