package com.sharker.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.sharker.R;
import com.sharker.models.FirstHand;
import com.sharker.models.data.AdBanner;
import com.sharker.models.data.CourseData;
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
            listTry("list_try", "", 0);
            listCourse("list_course", "", 0, 1);
            listTopic("list_topic", 0);
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
                listTry("list_try", "", 0);
                listCourse("list_course", "", 0, 1);
                listTopic("list_topic", 0);
            }
        });
    }

    void listBanner() {
        SharkerParams params = new SharkerParams("list_banner");
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

    void listTry(String uri, String category, int page_index) {
        listCourse(uri, category, page_index, 0);
    }

    void listTopic(String uri, int page_index) {
        listCourse(uri, "", page_index, 0);
    }

    public void listCourse(String uri, String category, int page_index, int sort_by) {
        SharkerParams params = new SharkerParams(uri);

        if (!TextUtils.isEmpty(category)) {
            params.addBodyParameter("category", category);
        }

        params.addBodyParameter("page_size", "25");

        params.addBodyParameter("page_index", "" + page_index);

        if (sort_by != 0) {
            params.addBodyParameter("sort_by", "" + sort_by);
        }

        x.http().post(params, new Callback.CommonCallback<CourseData>() {
            @Override
            public void onSuccess(CourseData result) {

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
