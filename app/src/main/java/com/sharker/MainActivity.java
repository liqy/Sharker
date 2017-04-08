package com.sharker;

import android.os.Bundle;
import android.util.Log;

import com.sharker.models.Banner;
import com.sharker.models.BaseData;
import com.sharker.models.FirstHand;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirstHand.getInstance();
        if (FirstHand.isHost()) {
            listBanner();
            listTry();
        } else {
            firstHand();
        }
    }

    public void firstHand() {
        SharkerParams params = new SharkerParams("first_hand");
        x.http().post(params, new Callback.CommonCallback<FirstHand>() {
            @Override
            public void onSuccess(FirstHand result) {
                FirstHand.save(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                getHost();
            }
        });

    }

    void getHost() {
        SharkerParams params = new SharkerParams("get_host");
        x.http().post(params, new Callback.CommonCallback<FirstHand>() {
            @Override
            public void onSuccess(FirstHand result) {
                FirstHand.saveHost(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                listBanner();
                listTry();
            }
        });
    }

    void listBanner() {
        SharkerParams params = new SharkerParams("list_banner");
        x.http().post(params, new Callback.CommonCallback<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {

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

    void listTry(){
        SharkerParams params = new SharkerParams("list_try");
//        params.addBodyParameter("category","001");
        params.addBodyParameter("page_size","20");
        params.addBodyParameter("page_index","0");
        x.http().post(params, new Callback.CommonCallback<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {

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
