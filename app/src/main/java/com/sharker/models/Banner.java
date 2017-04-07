package com.sharker.models;

import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 16:29
 */
public class Banner {
    public String image;
    public String click;

    @Override
    public String toString() {
        return "Banner{" +
                "image='" + image + '\'' +
                ", click='" + click + '\'' +
                '}';
    }
}
