package com.sharker.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sharker.R;
import com.sharker.models.data.AdBanner;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

public class DetailTopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_topic);
    }

    void detailTopic(String object_id){
        SharkerParams params = new SharkerParams("detail_topic");
        params.addBodyParameter("object_id",object_id);
        x.http().post(params, new Callback.CommonCallback<AdBanner>() {
            @Override
            public void onSuccess(AdBanner result) {

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
