package com.sharker.models.data;

import com.google.gson.annotations.SerializedName;
import com.sharker.models.Course;
import com.sharker.models.CourseCategory;
import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/8 16:55
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class CourseData extends BaseData{

    public List<CourseCategory> category;

    public String this_category;

    @SerializedName("try")
    public List<Course> tryCourse;

    @SerializedName("course")
    public List<Course> qualityCourse;

    @SerializedName("topic")
    public List<Course> topicCourse;


}
