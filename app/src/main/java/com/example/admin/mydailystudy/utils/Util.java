package com.example.admin.mydailystudy.utils;

import android.content.Context;

public class Util {
    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dip2px(float dpValue, Context context) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static int px2dip(int pxValue, Context context)
    {
        final float scale = getDensity(context);
        return (int)(pxValue / scale + 0.5f);
    }


    public static float getDensity(Context context) {
        float density = -1f;
        if (density < 0) {
            return context.getResources().getDisplayMetrics().density;
        }
        return 0;
    }
}
