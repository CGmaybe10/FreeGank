package com.freegank.http;

import android.content.Context;

import com.freegank.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moubiao on 2016/9/18.
 * retrofit的辅助类
 */
public class RetrofitHelper {

    private static Retrofit mRetrofit;

    private RetrofitHelper() {
    }

    public static <T> T getRetrofitService(Context context, final Class<T> cls) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit.create(cls);
    }
}
