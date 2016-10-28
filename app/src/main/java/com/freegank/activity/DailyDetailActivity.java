package com.freegank.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.adapter.DailyDetailAdapter;
import com.freegank.bean.CategoryData;
import com.freegank.bean.DailyDetailData;
import com.freegank.bean.DetailData;
import com.freegank.bean.IntentConstant;
import com.freegank.http.GankApiService;
import com.freegank.http.RetrofitHelper;
import com.freegank.interfaces.OnItemClickListener;
import com.freegank.util.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moubiao on 2016/9/30.
 * 每日详情的界面
 */

public class DailyDetailActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener {
    private final String TAG = "moubiao";

    private Intent mData;
    private String mDate;

    private List<DetailData> mDetailData;
    private DailyDetailAdapter mDetailAdapter;

    private RecyclerView mDailyDetailRV;
    private FloatingActionButton mVideoFAB;

    @Override
    public int getLayoutResId() {
        return R.layout.daily_detail_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                if (mStatusBarView == null) {
                    mStatusBarView = DisplayUtil.setupStatusBarView(this);
                }
            }

            mStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTranslucent));
        }
    }

    private void initData() {
        mDetailData = new ArrayList<>();
        mDetailAdapter = new DailyDetailAdapter(this, mDetailData);
        mDetailAdapter.setOnItemClickListener(this);

        mData = getIntent();
        mDate = mData.getStringExtra(IntentConstant.DAILY_DATE);
        GankApiService gankApiService = RetrofitHelper.getRetrofitService(getApplicationContext(), GankApiService.class);
        Call<DailyDetailData> dataCall = gankApiService.getDailyData(mDate);
        dataCall.enqueue(new Callback<DailyDetailData>() {
            @Override
            public void onResponse(Call<DailyDetailData> call, Response<DailyDetailData> response) {
                CategoryData categoryData = response.body().getResults();
                List<DetailData> android = categoryData.getAndroid();
                if (android != null) {
                    mDetailData.addAll(android);
                }
                List<DetailData> ios = categoryData.getiOS();
                if (ios != null) {
                    mDetailData.addAll(ios);
                }
                List<DetailData> web = categoryData.getWeb();
                if (web != null) {
                    mDetailData.addAll(web);
                }
                List<DetailData> resource = categoryData.getResource();
                if (resource != null) {
                    mDetailData.addAll(resource);
                }
                List<DetailData> random = categoryData.getRandom();
                if (random != null) {
                    mDetailData.addAll(random);
                }
                List<DetailData> video = categoryData.getVideo();
                if (video != null) {
                    mDetailData.addAll(video);
                }
                List<DetailData> app = categoryData.getApp();
                if (app != null) {
                    mDetailData.addAll(app);
                }
                mDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DailyDetailData> call, Throwable t) {
                Snackbar.make(mDailyDetailRV, getString(R.string.common_load_data_failed), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ((CollapsingToolbarLayout) findViewById(R.id.daily_detail_collapsing)).setTitle(mDate);

        ImageView headIMG = (ImageView) findViewById(R.id.daily_detail_head_img);
        Picasso.with(this)
                .load(mData.getStringExtra(IntentConstant.MEI_ZHI_URL))
                .into(headIMG);

        mDailyDetailRV = (RecyclerView) findViewById(R.id.daily_detail_rv);
        mDailyDetailRV.setAdapter(mDetailAdapter);
        mDailyDetailRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mVideoFAB = (FloatingActionButton) findViewById(R.id.video_fab);
        mVideoFAB.setOnClickListener(this);
    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent intent = new Intent(DailyDetailActivity.this, ItemDetailActivity.class);
        intent.putExtra(IntentConstant.CONTENT_URL, mDetailData.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DailyDetailActivity.this, ItemDetailActivity.class);
        intent.putExtra(IntentConstant.CONTENT_URL, mDetailData.get(mDetailData.size() - 1).getUrl());
        startActivity(intent);
    }
}
