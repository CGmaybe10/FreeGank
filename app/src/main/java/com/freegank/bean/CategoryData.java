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
    @SerializedName("拓展资源")
    private List<DetailData> resource;
    @SerializedName("瞎推荐")
    private List<DetailData> random;
    @SerializedName("福利")
    private List<DetailData> welfare;
    private List<DetailData> app;

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

    public List<DetailData> getResource() {
        return resource;
    }

    public void setResource(List<DetailData> resource) {
        this.resource = resource;
    }

    public List<DetailData> getRandom() {
        return random;
    }

    public void setRandom(List<DetailData> random) {
        this.random = random;
    }

    public List<DetailData> getWelfare() {
        return welfare;
    }

    public void setWelfare(List<DetailData> welfare) {
        this.welfare = welfare;
    }

    public List<DetailData> getApp() {
        return app;
    }

    public void setApp(List<DetailData> app) {
        this.app = app;
    }
}
