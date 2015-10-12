package com.library_wheel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2015/10/9.
 */
public class SelectTimeFragment extends Fragment implements OnWheelChangedListener {
    private WheelView mYearWheel;
    private WheelView mMonthWheel;
    private WheelView mDayWheel;
    private WheelView mHourWheel;
    private WheelView mMinuteWheel;

    private SelectTimeHelper helper;

    private int mSelectYear;
    private int mSelectMonth = 1;
    private int mSelectDay = 1;
    private int mSelectHour;
    private int mSelectMinute;
    private OnSelectTimeListener listener;
    private boolean isShowAll = true;

    public interface OnSelectTimeListener {
        void onSlectTime(int year, int month, int day, int hour, int minute);
    }

    public void setOnSelectTimeListener(OnSelectTimeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_time, container, false);
        mYearWheel = (WheelView) view.findViewById(R.id.id_year);
        mMonthWheel = (WheelView) view.findViewById(R.id.id_month);
        mDayWheel = (WheelView) view.findViewById(R.id.id_day);
        mHourWheel = (WheelView) view.findViewById(R.id.id_hour);
        mMinuteWheel = (WheelView) view.findViewById(R.id.id_minute);
        if (!isShowAll) {
            mHourWheel.setVisibility(View.GONE);
            mMinuteWheel.setVisibility(View.GONE);
        }
        mYearWheel.addChangingListener(this);
        mMonthWheel.addChangingListener(this);
        mDayWheel.addChangingListener(this);
        mHourWheel.addChangingListener(this);
        mMinuteWheel.addChangingListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (helper == null)
            helper = new SelectTimeHelper(getActivity());
        mYearWheel.setViewAdapter(helper.createAdapter(SelectTimeHelper.TimeFlag.YEAR, "%4d年"));
        mYearWheel.setCurrentItem(SelectTimeHelper.INTERVAL_YEAR - 20);
        mSelectYear = helper.getSelectYear(mYearWheel.getCurrentItem());
        //月
        mMonthWheel.setViewAdapter(helper.createAdapter(SelectTimeHelper.TimeFlag.MONTH, "%02d月"));
        mMonthWheel.setCurrentItem(0);
        mSelectMonth = mMonthWheel.getCurrentItem() + 1;
        //天
        updateDay();
        //小时
        mHourWheel.setViewAdapter(helper.createAdapter(SelectTimeHelper.TimeFlag.HOUR, "%02d点"));
        mHourWheel.setCurrentItem(0);
        mSelectHour = mHourWheel.getCurrentItem();
        //分钟
        mMinuteWheel.setViewAdapter(helper.createAdapter(SelectTimeHelper.TimeFlag.MINUTE, "%02d分"));
        mMinuteWheel.setCurrentItem(0);
        mSelectMinute = mMinuteWheel.getCurrentItem();
    }

    private void updateDay() {
        mDayWheel.setViewAdapter(helper.createDayAdapter(mSelectYear, mSelectMonth, "%02d日"));
        mDayWheel.setCurrentItem(0);
        mSelectDay = mDayWheel.getCurrentItem() + 1;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mYearWheel) {
            mSelectYear = helper.getSelectYear(newValue);
            updateDay();
        } else if (wheel == mMonthWheel) {
            mSelectMonth = newValue + 1;
            updateDay();
        } else if (wheel == mDayWheel) {
            mSelectDay = newValue + 1;
        } else if (wheel == mHourWheel) {
            mSelectHour = newValue;
        } else if (wheel == mMinuteWheel) {
            mSelectMinute = newValue;
        }
        if (listener != null) {
            listener.onSlectTime(mSelectYear, mSelectMonth, mSelectDay, mSelectHour, mSelectMinute);
        }
    }

    public static SelectTimeFragment newInstance() {
        return new SelectTimeFragment();
    }

    public void setShowAll(boolean flag) {
        isShowAll = flag;
    }
}
