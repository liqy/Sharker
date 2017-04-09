package com.sharker.models;

import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/9 15:44
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class AuthUser {
    public String session;
    public boolean user_new;
}
