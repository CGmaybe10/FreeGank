package com.freegank.util;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by moubiao on 2016/9/28.
 * 跟现实相关的工具类
 */

public class DisplayUtil {

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏view
     */
    public static View setupStatusBarView(Activity context) {
        ViewGroup contentLayout = (ViewGroup) context.findViewById(android.R.id.content);
        View statusBarView = new View(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getStatusBarHeight(context));
        contentLayout.addView(statusBarView, lp);
        return statusBarView;
    }

    /**
     * 设置状态栏颜色
     */
    public static View setupStatusBarColor(Activity context, int color) {
        View statusBarView = setupStatusBarView(context);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * 获取ActionBar的高度
     */
    public static int getActionBarHeight(Context context) {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return height;
    }
}
