package com.sharker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sharker.R;
import com.sharker.models.TopicDetail;
import com.sharker.models.data.AdBanner;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * 专辑详情
 */
public class DetailTopicActivity extends BaseActivity {

    public static void open(Activity activity) {
        Intent intent=new Intent(activity,DetailTopicActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_topic);
        detailTopic("300002");
    }

    void detailTopic(String object_id){
        SharkerParams params = new SharkerParams("detail_topic");
        params.addBodyParameter("object_id",object_id);
        x.http().post(params, new Callback.CommonCallback<TopicDetail>() {
            @Override
            public void onSuccess(TopicDetail result) {

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
