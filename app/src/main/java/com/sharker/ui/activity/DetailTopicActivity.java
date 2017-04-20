package com.sharker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;
import com.sharker.R;
import com.sharker.models.TopicDetail;
import com.sharker.models.data.ResponseData;
import com.sharker.network.RetrofitHelper;
import com.sharker.network.SharkerParams;
import com.sharker.network.volley.SharkerRequest;
import com.sharker.network.volley.SharkerVolley;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 专辑详情
 */
public class DetailTopicActivity extends BaseActivity implements Response.Listener, Response.ErrorListener {

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, DetailTopicActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_topic);
//        detailTopicRx("300002");
        helloWorld();
    }

    /**
     * Android Https相关完全解析 当OkHttp遇到Https
     */
    void helloWorld(){
        RetrofitHelper.getHelloAPI().helloworld().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Logger.d(s);
                    }
                });
    }

    /**
     * Retrofit+RxJava请求演示
     *
     * @param object_id
     */
    void detailTopicRx(String object_id) {
        Map<String, String> map = RetrofitHelper.createParams();
        map.put("object_id", object_id);

        RetrofitHelper.getUserAPI().detailTopic(RetrofitHelper.getParams(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<TopicDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseData<TopicDetail> firstHandResponseData) {

                    }
                });
    }

    void detailTopicVolley(String object_id) {
        Map<String, String> params = RetrofitHelper.createParams();
        params.put("object_id", object_id);
        SharkerRequest request=new SharkerRequest<TopicDetail>(
                "detail_topic", TopicDetail.class, params, this, this
        );
        SharkerVolley.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {

    }

    void detailTopic(String object_id) {
        SharkerParams params = new SharkerParams("detail_topic");
        params.addBodyParameter("object_id", object_id);
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
