package com.sharker.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.security.SecureRandom;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/6 21:57
 */

public abstract class BaseActivity extends AppCompatActivity {

    //TODO 注册一个EventBus事件，调用销毁函数
    SecureRandom secureRandom;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO  该销毁的资源判断，，例如取消网络请求，关闭数据库连接 ，关闭上下文环境

        //TODO 解除EventBus可以在最后一行
    }

}


