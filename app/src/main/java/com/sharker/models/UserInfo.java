package com.sharker.models;

import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/10 17:09
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class UserInfo {
    public String image;
    public String tel;
    public String name;
    public String wx_login;
}
