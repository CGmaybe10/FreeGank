package com.freegank.http;

import com.freegank.bean.BaseData;
import com.freegank.bean.CategoryData;
import com.freegank.bean.DailyData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.bean.DetailData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by moubiao on 2016/9/13.
 * gank的接口
 */
public interface GankApiService {
    @GET("history/content/{quantity}/{page}")
    Call<BaseData<DailyOverviewData>> getDailyOverview(@Path("quantity") String quantity, @Path("page") String page);

    @GET("data/{category}/{quantity}/{page}")
    Call<BaseData<DetailData>> getCategoryData(@Path("category") String category, @Path("quantity") String quantity, @Path("page") String page);

    @GET("day/{date}")
    Call<DailyData> getDailyData(@Path("date") String date);
}
