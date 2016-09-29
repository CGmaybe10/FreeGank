package com.freegank.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.freegank.R;
import com.freegank.bean.IntentConstant;
import com.freegank.util.DisplayUtil;
import com.freegank.util.FileUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by moubiao on 2016/9/27.
 * 查看图片的界面
 */

public class MeiZhiActivity extends BaseActivity {
    private final String TAG = "moubiao";

    private String IMG_URL;

    private ImageView mMeiZhiIMG;
    private Bitmap mBitmap;

    private Subscription mSubscription;

    @Override
    public int getLayoutResId() {
        return R.layout.mei_zhi_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        Intent data = getIntent();
        IMG_URL = data.getStringExtra(IntentConstant.MEI_ZHI_URL);
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTranslationY(-DisplayUtil.getActionBarHeight(this) * 2);
        isToolBarHiding = true;
        mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        mMeiZhiIMG = (ImageView) findViewById(R.id.mei_zhi_img);
        Picasso.with(this)
                .load(IMG_URL)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_error)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mMeiZhiIMG.setImageBitmap(bitmap);
                        mBitmap = bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        mMeiZhiIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideToolbar();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meizhi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.save_mei_zhi_img:
                saveMeiZhiImg();
                break;
            case R.id.share_mei_zhi_menu:
                sharedMeiZhiImg();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存图片
     */
    private void saveMeiZhiImg() {
        mSubscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                if (mBitmap == null || !FileUtil.isSDCardEnable()) {
                    Snackbar.make(mMeiZhiIMG, getString(R.string.common_save_failed), Snackbar.LENGTH_SHORT).show();
                } else {
                    String[] fileNames = IMG_URL.split(File.separator);
                    Uri uri = FileUtil.saveBitmapToSDCard(mBitmap, fileNames[fileNames.length - 1], getString(R.string.app_name));
                    if (uri == null) {
                        subscriber.onError(new Exception(getString(R.string.common_save_failed)));
                    } else {
                        subscriber.onNext(uri);
                        subscriber.onCompleted();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mMeiZhiIMG, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        sendBroadcast(scannerIntent);
                        Snackbar.make(mMeiZhiIMG, getString(R.string.common_save_success), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void sharedMeiZhiImg() {
        Snackbar.make(mMeiZhiIMG, "暂不支持", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
