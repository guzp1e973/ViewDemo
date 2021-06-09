package com.example.lifeassistant.util.calendar;

import java.util.Calendar;

import com.example.lifeassistant.util.Constants;


/**
 * 对公历日期的处理类
 */
public class GregorianUtil {
   
    private int mMonth;
    private int mDay;

    public GregorianUtil(Calendar calendar) {
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);
    }

    public String getGremessage() {
        return Constants.GRE_FESTVIAL[mMonth][mDay - 1];
    }

}
