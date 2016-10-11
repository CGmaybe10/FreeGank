package com.freegank.constant;

/**
 * Created by moubiao on 2016/10/11.
 * 加载数据时的状态
 */

public class DataStatus {
    public static final int VIEW_TYPE_HAS_DATE = 0;
    public static final int VIEW_TYPE_DIFF_STATUS = 1;

    public enum Status {
        /**
         * 正在加载
         */
        LOADING,
        /**
         * 空数据
         */
        EMPTY,
        /**
         * 加载失败
         */
        ERROR,
        /**
         * 有数据
         */
        HAS_DATA
    }
}
