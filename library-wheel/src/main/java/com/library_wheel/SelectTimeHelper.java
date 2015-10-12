package com.library_wheel;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by Administrator on 2015/10/9.
 */
public class SelectTimeHelper {
    public final static int INTERVAL_YEAR = 120;//年份间隔
    private Context mContext;
    private GregorianCalendar mCalendar;

    final int[] LARGE_MONTH = {1, 3, 5, 7, 8, 10, 12};

    public enum TimeFlag {
        YEAR, MONTH, HOUR, MINUTE
    }

    public SelectTimeHelper(Context context) {
        mContext = context;
        mCalendar = new GregorianCalendar();
    }

    public static boolean isLeapYear(Date date) {
        GregorianCalendar calendar1 = new GregorianCalendar();
        calendar1.setTime(date);
        int year = calendar1.get(Calendar.YEAR);
        return calendar1.isLeapYear(year);
    }

    public NumericWheelAdapter createAdapter(TimeFlag flag, String format) {
        if (flag == TimeFlag.YEAR) {
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            int year = mCalendar.get(Calendar.YEAR);
            return new NumericWheelAdapter(mContext, year - INTERVAL_YEAR, year, format);
        } else if (flag == TimeFlag.MONTH) {
            return new NumericWheelAdapter(mContext, 1, 12, format);
        } else if (flag == TimeFlag.MINUTE) {
            return new NumericWheelAdapter(mContext, 0, 59, format);
        } else if (flag == TimeFlag.HOUR) {
            return new NumericWheelAdapter(mContext, 0, 23, format);
        } else {
            return null;
        }
    }

    public NumericWheelAdapter createDayAdapter(int year, int month, String format) {
        if (month <=0 || month > 12) {
            throw new UnsupportedOperationException("month is not legitimate");
        }
        if (isLargeMonth(month))
            return new NumericWheelAdapter(mContext, 1, 31, format);
        else if (month == 2) {
            if (new GregorianCalendar().isLeapYear(year)) {
                return new NumericWheelAdapter(mContext, 1, 29, format);
            } else
                return new NumericWheelAdapter(mContext, 1, 28, format);
        } else
            return new NumericWheelAdapter(mContext, 1, 30, format);
    }


    private boolean isLargeMonth(int month) {
        for (int i = 0; i < LARGE_MONTH.length; i++) {
            if (month == LARGE_MONTH[i])
                return true;
        }
        return false;
    }

    public int getSelectYear(int currentIndex) {
        if (currentIndex < 0 || currentIndex > INTERVAL_YEAR)
            throw new RuntimeException("currentIndex is wrong");
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        return mCalendar.get(Calendar.YEAR) - (INTERVAL_YEAR - currentIndex);
    }
}
