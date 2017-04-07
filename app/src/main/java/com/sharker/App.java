package com.sharker;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.xutils.x;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/6 21:52
 * https://github.com/futurice/android-best-practices
 */

public class App extends Application {

    public static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Stetho.initializeWithDefaults(this);
        com.blankj.utilcode.util.Utils.init(this);

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
