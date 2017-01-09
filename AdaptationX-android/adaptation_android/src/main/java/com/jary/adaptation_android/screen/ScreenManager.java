package com.jary.adaptation_android.screen;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by jary on 2017/1/4.
 */

public class ScreenManager {

    public static int getScreenWidth(Context mContext){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context mContext){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScreenDensityDpi(Context mContext){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

}
