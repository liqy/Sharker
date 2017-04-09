package com.sharker.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.sharker.R;
import com.sharker.models.FirstHand;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirstHand.getInstance();
        if (!FirstHand.isHost()){
            firstHand();
        }
    }

    public void firstHand() {
        SharkerParams params = new SharkerParams("first_hand");
        x.http().post(params, new Callback.CommonCallback<FirstHand>() {
            @Override
            public void onSuccess(FirstHand result) {
                FirstHand.save(result);
                getHost();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //TODO 处理网络错误
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //TODO 处理网络错误
            }

            @Override
            public void onFinished() {

            }
        });

    }

    void getHost() {
        SharkerParams params = new SharkerParams("get_host");
        x.http().post(params, new Callback.CommonCallback<FirstHand>() {
            @Override
            public void onSuccess(FirstHand result) {
                FirstHand.saveHost(result);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //TODO 处理网络错误
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //TODO 处理网络错误
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
