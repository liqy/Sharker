package com.sharker.models;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 15:00
 */

public class ResponseListData<T> {
    public int ret;

    public String msg;

    public List<T> data;

    public boolean isResponseOk() {
        return ret == 0;
    }

}
