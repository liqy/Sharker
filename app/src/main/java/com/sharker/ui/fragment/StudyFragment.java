package com.sharker.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharker.R;
import com.sharker.models.FirstHand;
import com.sharker.models.data.AdBanner;
import com.sharker.models.data.CourseData;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public StudyFragment() {
        // Required empty public constructor
    }

    public static StudyFragment newInstance(String param1, String param2) {
        StudyFragment fragment = new StudyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (FirstHand.isHost()) {
            listBanner();
            listTry("list_try", "", 0);
            listCourse("list_course", "", 0, 1);
            listTopic("list_topic", 0);
        }
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
