package com.sharker.models;

import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 16:50
 */

@HttpResponse(parser = SharkerResponseParser.class)
public class BaseData {
    public List<Banner> banner;
}
