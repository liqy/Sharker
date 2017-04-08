package com.sharker.models.data;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 09:55
 */

public class ResponseData<T> {

    public int ret;

    public String msg;

    public T data;

    public boolean isResponseOk() {
        return ret == 0;
    }





}
