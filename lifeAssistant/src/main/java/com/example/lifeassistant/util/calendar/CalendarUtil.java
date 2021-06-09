package com.example.lifeassistant.util.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.lifeassistant.util.Constants;


/**
 * 
 * 把公历时间处理成农历时间
 * 
 */
public class CalendarUtil {

	/**
	 * 转换为2012年11月22日格式
	 */
	private static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
			"yyyy年MM月dd日");
	/**
	 * 转换为2012-11-22格式
	 */
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 计算得到农历的年份
	 */
	private int mLuchYear;
	/**
	 * 计算得到农历的月份
	 */
	private int mLuchMonth;

	/**
	 * 计算得到农历的日期
	 */
	private int mLuchDay;

	/**
	 * 用于标识是事为闰年
	 */
	private boolean isLoap;

	/**
	 * 用于记录当前处理的时间
	 */
	private Calendar mCurrenCalendar;

	/**
	 * 传回农历 year年的总天数
	 * 
	 * @param year
	 *            将要计算的年份
	 * @return 返回传入年份的总天数
	 */
	private static int yearDays(int year) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((Constants.LUNAR_INFO[year - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(year));
	}

	/**
	 * 传回农历 year年闰月的天数
	 * 
	 * @param year
	 *            将要计算的年份
	 * @return 返回 农历 year年闰月的天数
	 */
	private static int leapDays(int year) {
		if (leapMonth(year) != 0) {
			if ((Constants.LUNAR_INFO[year - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	/**
	 * 传回农历 year年闰哪个月 1-12 , 没闰传回 0
	 * 
	 * @param year
	 *            将要计算的年份
	 * @return 传回农历 year年闰哪个月 1-12 , 没闰传回 0
	 */
	private static int leapMonth(int year) {
		return (int) (Constants.LUNAR_INFO[year - 1900] & 0xf);
	}

	/**
	 * 传回农历 year年month月的总天数
	 * 
	 * @param year
	 *            将要计算的年份
	 * @param month
	 *            将要计算的月份
	 * @return 传回农历 year年month月的总天数
	 */
	private static int monthDays(int year, int month) {
		if ((Constants.LUNAR_INFO[year - 1900] & (0x10000 >> month)) == 0)
			return 29;
		else
			return 30;
	}

	/**
	 * 传回农历 y年的生肖
	 * 
	 * @return 传回农历 y年的生肖
	 */
	public String animalsYear() {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇",
				"马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(mLuchYear - 4) % 12];
	}

	// ====== 传入 月日的offset 传回干支, 0=甲子
	private static String cyclicalm(int num) {
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚",
				"辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午",
				"未", "申", "酉", "戌", "亥" };

		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ====== 传入 offset 传回干支, 0=甲子
	public String cyclical() {
		int num = mLuchYear - 1900 + 36;
		return (cyclicalm(num));
	}

	/**
	 * 传出y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
	 * dayCyl5:与1900年1月31日相差的天数,再加40 ?
	 * 
	 * @param cal
	 * @return
	 */
	public CalendarUtil(Calendar cal) {
		int yearCyl, monCyl, dayCyl;
		mCurrenCalendar = cal;
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = chineseDateFormat.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
			// Options | File Templates.
		}

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
		dayCyl = offset + 40;
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
		int iYear, daysOfYear = 0;
		for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
			daysOfYear = yearDays(iYear);
			offset -= daysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += daysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// 农历年份
		mLuchYear = iYear;

		yearCyl = iYear - 1864;
		leapMonth = leapMonth(iYear); // 闰哪个月,1-12
		isLoap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !isLoap) {
				--iMonth;
				isLoap = true;
				daysOfMonth = leapDays(mLuchYear);
			} else
				daysOfMonth = monthDays(mLuchYear, iMonth);

			offset -= daysOfMonth;
			// 解除闰月
			if (isLoap && iMonth == (leapMonth + 1))
				isLoap = false;
			if (!isLoap)
				monCyl++;
		}
		// offset为0时，并且刚才计算的月份是闰月，要校正
		if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
			if (isLoap) {
				isLoap = false;
			} else {
				isLoap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offset小于0时，也要校正
		if (offset < 0) {
			offset += daysOfMonth;
			--iMonth;
			--monCyl;

		}
		mLuchMonth = iMonth;
		mLuchDay = offset + 1;
	}

	/**
	 * 返化成中文格式
	 * 
	 * @param day
	 * @return
	 */
	public static String getChinaDayString(int day) {
		String chineseTen[] = { "初", "十", "廿", "卅" };
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30)
			return "";
		if (day == 10)
			return "初十";
		else
			return chineseTen[day / 10] + Constants.CHINESE_NUMBER[n];
	}

	/**
	 * 用于显示农历的初几这种格式
	 * 
	 * @return 农历的日期
	 */
	public String toString() {
		String message = "";
		int n = mLuchDay % 10 == 0 ? 9 : mLuchDay % 10 - 1;
		message = getChinaCalendarMsg(mLuchYear, mLuchMonth, mLuchDay);
		if (StringUtil.isNullOrEmpty(message)) {
			String solarMsg = new SolarTermsUtil(mCurrenCalendar)
					.getSolartermsMsg();
			// 判断当前日期是否为节气
			if (!StringUtil.isNullOrEmpty(solarMsg)) {
				message = solarMsg;
			} else {
				/**
				 * 判断当前日期是否为公历节日
				 */
				String gremessage = new GregorianUtil(mCurrenCalendar)
						.getGremessage();
				if (!StringUtil.isNullOrEmpty(gremessage)) {
					message = gremessage;
				} else if (mLuchDay == 1) {
					message = Constants.CHINESE_NUMBER[mLuchMonth - 1] + "月";
				} else {
					message = getChinaDayString(mLuchDay);
				}

			}
		}
		return message;
	}

	/**
	 * 返回农历的年月日
	 * 
	 * @return 农历的年月日格式
	 */
	public String getDay() {
		return (isLoap ? "闰" : "") + Constants.CHINESE_NUMBER[mLuchMonth - 1]
				+ "月" + getChinaDayString(mLuchDay);
	}

	/**
	 * 把calendar转化为当前年月日
	 * 
	 * @param calendar
	 *            Calendar
	 * @return 返回成转换好的 年月日格式
	 */
	public static String getDay(Calendar calendar) {
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 用于比对二个日期的大小
	 * 
	 * @param compareDate
	 *            将要比对的时间
	 * @param currentDate
	 *            当前时间
	 * @return true 表示大于当前时间 false 表示小于当前时间
	 */
	public static boolean compare(Date compareDate, Date currentDate) {
		return chineseDateFormat.format(compareDate).compareTo(
				chineseDateFormat.format(currentDate)) >= 0;
	}

	/**
	 * 获取当前周几
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getWeek(Calendar calendar) {
		return "周"
				+ Constants.WEEK_NUMBER[calendar.get(Calendar.DAY_OF_WEEK) - 1]
				+ "";
	}

	/**
	 * 将当前时间转换成要展示的形式
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getCurrentDay(Calendar calendar) {
		return getDay(calendar) + " 农历" + new CalendarUtil(calendar).getDay()
				+ " " + getWeek(calendar);
	}

	/**
	 * 用于获取中国的传统节日
	 * 
	 * @param month
	 *            农历的月
	 * @param day
	 *            农历日
	 * @return 中国传统节日
	 */
	private String getChinaCalendarMsg(int year, int month, int day) {
		String message = "";
		if (((month) == 1) && day == 1) {
			message = "春节";
		} else if (((month) == 1) && day == 15) {
			message = "元宵";
		} else if (((month) == 5) && day == 5) {
			message = "端午";
		} else if ((month == 7) && day == 7) {
			message = "七夕";
		} else if (((month) == 8) && day == 15) {
			message = "中秋";
		} else if ((month == 9) && day == 9) {
			message = "重阳";
		} else if ((month == 12) && day == 8) {
			message = "腊八";
		} else {
			if (month == 12) {
				if ((((monthDays(year, month) == 29) && day == 29))
						|| ((((monthDays(year, month) == 30) && day == 30)))) {
					message = "除夕";
				}
			}
		}
		return message;
	}

}
