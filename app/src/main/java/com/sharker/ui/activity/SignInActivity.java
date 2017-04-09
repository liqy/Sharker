package com.sharker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sharker.R;

/**
 * 注册
 */
public class SignInActivity extends BaseActivity {

    public static void open(Activity activity) {
        Intent intent=new Intent(activity,SignInActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}
