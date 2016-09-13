package com.freegank.bean;

import java.util.List;

/**
 * Created by moubiao on 2016/9/13.
 * gank的基本数据类型
 */
public class BaseData<T> {

    private boolean error;

    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
