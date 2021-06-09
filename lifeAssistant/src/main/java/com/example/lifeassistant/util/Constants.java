package com.example.lifeassistant.util;

import com.example.lifeassistant.R;

import android.widget.ImageView;

public class Constants {
	public static final int NOTEPAD_ADD_RESULT = 12;
	public static final String EXPRESS_URL = "http://v.juhe.cn/exp/index?key=91904cdf74040d19eada2fe8949523cd&com=company&no=postid";
	/*----------通讯录模块---------------*/
	public static final String DATABASE_NAME = "contactsDB";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "Contacts";

	public static final String C_ID = "_id";
	public static final String C_CONTACTS_NAME = "contacts_name";
	public static final String C_CONTACTS_NUMBER = "contacts_number";

	/**
	 * 用于传递选中的日期
	 */
	public static final String MESSAGE = "msg";

	/**
	 * 用于保存中文的月份
	 */
	public final static String CHINESE_NUMBER[] = { "一", "二", "三", "四", "五",
			"六", "七", "八", "九", "十", "十一", "腊" };

	/**
	 * 用于保存展示周几使用
	 */
	public final static String WEEK_NUMBER[] = { "日", "一", "二", "三", "四", "五",
			"六" };

	public final static long[] LUNAR_INFO = new long[] { 0x04bd8, 0x04ae0,
			0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
			0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
			0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
			0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
			0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
			0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
			0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
			0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
			0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
			0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
			0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
			0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
			0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
			0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
			0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
			0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
			0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
			0x0ada0 };

	public final static String[][] GRE_FESTVIAL = {
			// 一月
			{ "元旦", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 二月
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "情人", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 三月
			{ "", "", "", "", "", "", "", "妇女", "", "", "", "植树", "", "警察", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" },
			// 四月
			{ "愚人", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 五月
			{ "劳动", "", "", "青年", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" },
			// 六月
			{ "儿童", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 七月
			{ "建党", "", "", "", "", "", "抗战", "", "", "", "人口", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" },

			// 八月
			{ "建军", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 九月
			{ "", "", "", "", "", "", "", "", "", "教师", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 十月
			{ "国庆", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 十一月
			{ "", "", "", "", "", "", "", "", "", "", "光棍", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			// 十二月
			{ "艾滋病", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "圣诞", "", "", "", "",
					"", "" }, };
	public static final int CAL_LAYOUT_ID = 55;
	public static final int SWIPE_MIN_DISTANCE = 120;
	public static final int SWIPE_MAX_OFF_PATH = 250;
	public static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	public static String name;
	public static void setWeatherImg(ImageView iv, String weather) {
		if ((weather.equals("晴")) || (weather.equals("晴转多云"))) {
			iv.setBackgroundResource(R.drawable.d_sunny);
			return;
		}
		if ((weather.equals("多云")) || (weather.equals("多云转晴"))) {
			iv.setBackgroundResource(R.drawable.d_sunny_cloudy);
			return;
		}
		if (weather.contains("阴")) {
			iv.setBackgroundResource(R.drawable.d_cloudy);
			return;
		}
		if (weather.contains("雨")) {
			iv.setBackgroundResource(R.drawable.d_rain);
			return;
		}
		// iv.setBackgroundResource(2130837533);
	}
}
