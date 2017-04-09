package com.sharker.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.sharker.R;
import com.sharker.models.FirstHand;
import com.sharker.models.data.AdBanner;
import com.sharker.models.data.CourseData;
import com.sharker.network.SharkerParams;
import com.sharker.ui.fragment.CourseFragment;
import com.sharker.ui.widget.IconTextView;

import org.xutils.common.Callback;
import org.xutils.x;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

    void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PageBottomTabLayout tab = (PageBottomTabLayout) findViewById(R.id.tab);

        //注意这里调用了custom()方法
        final NavigationController navigationController = tab.custom()
                .addItem(newItem(R.mipmap.common_course_unselected,R.mipmap.common_course_selected,"学习"))
                .addItem(newItem(R.mipmap.common_study_unselected,R.mipmap.common_study_selected,"课程"))
                .addItem(newItem(R.mipmap.common_mine_unselected,R.mipmap.common_mine_selected,"我的"))
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CourseFragment.newInstance("","");
            }

            @Override
            public int getCount() {
                return navigationController.getItemCount();
            }
        });
        navigationController.setupWithViewPager(viewPager);
    }




    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        IconTextView normalItemView = new IconTextView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
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
