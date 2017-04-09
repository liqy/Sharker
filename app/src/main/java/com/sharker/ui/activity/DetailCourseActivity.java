package com.sharker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sharker.R;
import com.sharker.models.CourseDetail;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * 课程详情
 */
public class DetailCourseActivity extends BaseActivity {

    public static void open(Activity activity) {
        Intent intent=new Intent(activity,DetailCourseActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        detailCourse("200002");
    }

    void detailCourse(String object_id){
        SharkerParams params = new SharkerParams("detail_course");
        params.addBodyParameter("object_id",object_id);
        x.http().post(params, new Callback.CommonCallback<CourseDetail>() {
            @Override
            public void onSuccess(CourseDetail result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
