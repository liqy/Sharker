package com.sharker.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.sharker.R;
import com.sharker.lazyviewpager.LazyFragmentPagerAdapter;
import com.sharker.ui.fragment.CourseFragment;
import com.sharker.ui.fragment.SelfFragment;
import com.sharker.ui.fragment.StudyFragment;
import com.sharker.ui.widget.IconTextView;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PageBottomTabLayout tab = (PageBottomTabLayout) findViewById(R.id.tab);

        //注意这里调用了custom()方法
        final NavigationController navigationController = tab.custom()
                .addItem(newItem(R.mipmap.common_course_unselected, R.mipmap.common_course_selected, "学习"))
                .addItem(newItem(R.mipmap.common_study_unselected, R.mipmap.common_study_selected, "课程"))
                .addItem(newItem(R.mipmap.common_mine_unselected, R.mipmap.common_mine_selected, "我的"))
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new LazyFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            protected Fragment getItem(ViewGroup container, int position) {
                if (position == 0) {
                    return StudyFragment.newInstance("", "");
                } else if (position == 1) {
                    return CourseFragment.newInstance("", "");
                } else if (position == 2) {
                    return SelfFragment.newInstance("", "");
                } else {
                    return StudyFragment.newInstance("", "");
                }
            }

            @Override
            public int getCount() {
                return navigationController.getItemCount();
            }
        });
        navigationController.setupWithViewPager(viewPager);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        IconTextView normalItemView = new IconTextView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }




}
