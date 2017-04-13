package com.sharker.models;

import com.google.gson.annotations.SerializedName;
import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/9 16:56
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class CourseDetail {
    public int try_length;
    public int comment_num;
    @SerializedName("abstract")
    public String info;
    public int volume;
    public String title2;
    public String course_video;
    public String title;
    public String speaker_head;
    public String object_id;
    public int exam_score;
    public String service_tel;
    public String speaker_audio;
}
