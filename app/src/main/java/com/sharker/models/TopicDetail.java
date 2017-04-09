package com.sharker.models;

import com.google.gson.annotations.SerializedName;
import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/9 17:03
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class TopicDetail {
    public String topic_image;
    public String title;
    @SerializedName("abstract")
    public String info;
    public String object_id;
    public String title2;
    List<Course> course;
}
