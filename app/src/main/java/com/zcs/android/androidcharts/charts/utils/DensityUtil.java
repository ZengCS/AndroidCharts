package com.zcs.android.androidcharts.charts.utils;

import android.content.Context;

public class DensityUtil {
    private float mDensity = -1F;

    public DensityUtil(Context context) {
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    public int dip2px(float dpValue) {
        return (int) (dpValue * mDensity + 0.5F);
    }

    public int px2dip(float pxValue) {
        return (int) (pxValue / mDensity + 0.5F);
    }
}
