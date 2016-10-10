package com.freegank.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moubiao on 2016/9/13.
 * gank每日概要数据
 */
public class DailyOverviewData implements Parcelable {
    private String _id;
    private String content;
    private String publishedAt;
    private String title;

    private DailyOverviewData(Parcel in) {
        _id = in.readString();
        content = in.readString();
        publishedAt = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(content);
        dest.writeString(publishedAt);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailyOverviewData> CREATOR = new Creator<DailyOverviewData>() {
        @Override
        public DailyOverviewData createFromParcel(Parcel in) {
            return new DailyOverviewData(in);
        }

        @Override
        public DailyOverviewData[] newArray(int size) {
            return new DailyOverviewData[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
