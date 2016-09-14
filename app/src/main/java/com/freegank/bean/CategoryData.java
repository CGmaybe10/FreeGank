package com.freegank.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moubiao on 2016/9/14.
 */
public class CategoryData {
    private List<DetailData> Android;
    private List<DetailData> iOS;
    @SerializedName("休息视频")
    private List<DetailData> video;
    @SerializedName("Resource")
    private List<DetailData> 拓展资源;
    @SerializedName("random")
    private List<DetailData> 瞎推荐;
    @SerializedName("welfare")
    private List<DetailData> 福利;
    private List<DetailData> App;

    public List<DetailData> getAndroid() {
        return Android;
    }

    public void setAndroid(List<DetailData> android) {
        Android = android;
    }

    public List<DetailData> getiOS() {
        return iOS;
    }

    public void setiOS(List<DetailData> iOS) {
        this.iOS = iOS;
    }

    public List<DetailData> getVideo() {
        return video;
    }

    public void setVideo(List<DetailData> video) {
        this.video = video;
    }

    public List<DetailData> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<DetailData> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<DetailData> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<DetailData> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<DetailData> get福利() {
        return 福利;
    }

    public void set福利(List<DetailData> 福利) {
        this.福利 = 福利;
    }

    public List<DetailData> getApp() {
        return App;
    }

    public void setApp(List<DetailData> app) {
        App = app;
    }
}
