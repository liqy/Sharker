package com.sharker.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sharker.App;
import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 12:45
 */

@HttpResponse(parser = SharkerResponseParser.class)
public class FirstHand {
    private static volatile FirstHand singleton;
    public String app_id;
    public String private_key;
    public String url_host;

    public static void save(FirstHand hand) {
        singleton = hand;
        SharedPreferences preferences = App.getInstance().getSharedPreferences("FirstHand", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("app_id", hand.app_id);
        editor.putString("private_key", hand.private_key);
        editor.apply();
    }

    public static void saveHost(FirstHand hand) {
        singleton.url_host = hand.url_host;
        SharedPreferences preferences = App.getInstance().getSharedPreferences("FirstHand", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("host", hand.url_host);
        editor.apply();
    }

    public FirstHand() {
    }

    public static FirstHand getInstance() {
        if (singleton == null) {
            synchronized (FirstHand.class) {
                if (singleton == null) {
                    singleton = new FirstHand();
                    parseHand();
                }
            }
        }
        if (!isHand() && !isHost()) {
            parseHand();
        }
        return singleton;
    }

    private static void parseHand() {
        SharedPreferences preferences = App.getInstance().getSharedPreferences("FirstHand", Context.MODE_PRIVATE);
        singleton.app_id = preferences.getString("app_id", "");
        singleton.private_key = preferences.getString("private_key", "");
        singleton.url_host = preferences.getString("host", "");
    }

    public static boolean isHost() {
        return singleton != null && !TextUtils.isEmpty(singleton.url_host);
    }

    public static boolean isHand() {
        return singleton != null
                && !TextUtils.isEmpty(singleton.app_id)
                && !TextUtils.isEmpty(singleton.private_key);
    }

    @Override
    public String toString() {
        return "FirstHand{" +
                "app_id='" + app_id + '\'' +
                ", private_key='" + private_key + '\'' +
                ", url_host='" + url_host + '\'' +
                '}';
    }
}
