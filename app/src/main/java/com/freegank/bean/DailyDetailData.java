package com.freegank.bean;

import java.util.List;

/**
 * Created by moubiao on 2016/9/14.
 * gank每日数据
 */
public class DailyDetailData {

    private boolean error;
    private List<String> category;
    private CategoryData results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public CategoryData getResults() {
        return results;
    }

    public void setResults(CategoryData results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
