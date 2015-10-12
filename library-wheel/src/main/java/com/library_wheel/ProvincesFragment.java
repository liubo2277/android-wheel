package com.library_wheel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * 省市级三级联动
 * Created by Administrator on 2015/10/9.
 */
public class ProvincesFragment extends Fragment implements OnWheelChangedListener {
    static final String FLAG_PROVINCE = "flag_province";
    static final String FLAG_CITY = "flag_city";
    static final String FLAG_DISTRICT = "flag_district";


    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private String[] mProvinceDatas;

    /**
     * key - 省 value - 市
     */
    private HashMap<String, String[]> mCitisDatasMap;
    /**
     * key - 市 values - 区
     */
    private HashMap<String, String[]> mDistrictDatasMap;

    private String mCurrentProviceName = "--";
    private String mCurrentCityName = "--";
    private String mCurrentDistrictName = "--";

    private OnSelectListener listener;

    /**
     * 选择结果的回调接口
     */
    public interface OnSelectListener {
        void onSelect(String province, String city, String area);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provinces, container, false);
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mProvinceDatas = bundle.getStringArray(FLAG_PROVINCE);
        mCitisDatasMap = (HashMap<String, String[]>) bundle.getSerializable(FLAG_CITY);
        mDistrictDatasMap = (HashMap<String, String[]>) bundle.getSerializable(FLAG_DISTRICT);
        setUpData();
    }

    private void setUpData() {
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), mProvinceDatas));
        mCurrentProviceName = mProvinceDatas[mViewProvince.getCurrentItem()];
        updateCities(mCurrentProviceName);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {//更新省份
            mCurrentProviceName = mProvinceDatas[newValue];
            updateCities(mCurrentProviceName);
        } else if (wheel == mViewCity) {//更新城市
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[newValue];
            updateAreas(mCurrentCityName);
        } else if (wheel == mViewDistrict) {//更新地区
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
        }
        if (listener != null) {
            listener.onSelect(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
        }
    }

    /**
     * 更新城市
     */
    private void updateCities(String currentProvice) {
        String[] cities = mCitisDatasMap.get(currentProvice);
        if (cities == null)
            cities = new String[]{"--"};
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), cities));
        mViewCity.setCurrentItem(0);
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[0];
        updateAreas(mCurrentCityName);
    }

    /**
     * 更新地区
     */
    private void updateAreas(String currentCity) {
        String[] areas = mDistrictDatasMap.get(currentCity);
        if (areas == null)
            areas = new String[]{"--"};
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
    }

    public static ProvincesFragment newInstance(String[] provinceDatas, HashMap<String, String[]> cityDatas, HashMap<String, String[]> districtDatas) {
        if (provinceDatas == null || cityDatas == null || districtDatas == null)
            return null;
        ProvincesFragment fragment = new ProvincesFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(FLAG_PROVINCE, provinceDatas);
        bundle.putSerializable(FLAG_CITY, cityDatas);
        bundle.putSerializable(FLAG_DISTRICT, districtDatas);
        fragment.setArguments(bundle);
        return fragment;
    }

}
