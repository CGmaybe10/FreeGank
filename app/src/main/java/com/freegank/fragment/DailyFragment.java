package com.freegank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.freegank.R;
import com.freegank.adapter.DailyAdapter;
import com.freegank.bean.BaseData;
import com.freegank.bean.DailyOverviewData;
import com.freegank.http.GankApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moubiao on 2016/9/14.
 */
public class DailyFragment extends Fragment {
    private final String TAG = "moubiao";
    private final String REGEX = "\\b((https|http|ftp|rtsp|mms):\\/\\/)[^\\s]+.(jpg|jpeg|png)\\b";

    private Context mContext;
    private RecyclerView mContentRY;
    private TextView mTitleTV;

    private DailyAdapter mDailyAdapter;
    private List<DailyOverviewData> mDailyData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_overview, container, false);
        initView(view);
        return view;
    }

    private void initData() {
        mContext = getActivity();
        mDailyData = new ArrayList<>();
        mDailyAdapter = new DailyAdapter(mContext, mDailyData);
    }

    private void initView(View view) {
        mContentRY = (RecyclerView) view.findViewById(R.id.content_ry);
        mContentRY.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mContentRY.setAdapter(mDailyAdapter);

        Button button = (Button) view.findViewById(R.id.request_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GankApiService gankService = retrofit.create(GankApiService.class);
                Call<BaseData<DailyOverviewData>> call = gankService.getDailyOverview(String.valueOf(10), String.valueOf(1));
                call.enqueue(new Callback<BaseData<DailyOverviewData>>() {
                    @Override
                    public void onResponse(Call<BaseData<DailyOverviewData>> call, Response<BaseData<DailyOverviewData>> response) {
                        BaseData<DailyOverviewData> data = response.body();
                        List<DailyOverviewData> daily = data.getResults();

                        for (DailyOverviewData da : daily) {
                            String s = da.getContent();

                            Pattern pattern = Pattern.compile(REGEX);
                            Matcher matcher = pattern.matcher(s);

                            if (matcher.find()) {
                                da.setContent(matcher.group());
//                                Log.d(TAG, "onClick: Found value0: " + matcher.group());
                            } else {
                                da.setContent(null);
                            }
                        }
                        DailyOverviewData d = daily.get(0);
                        String content = d.getContent();
                        mDailyData.addAll(daily);
                        mDailyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseData<DailyOverviewData>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
