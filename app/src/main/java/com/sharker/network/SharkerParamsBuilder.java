package com.sharker.network;

import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.sharker.App;
import com.sharker.models.FirstHand;
import com.sharker.utils.Md5;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;

import java.util.List;

import javax.net.ssl.SSLSocketFactory;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 13:32
 */

public class SharkerParamsBuilder implements ParamsBuilder {

    @Override
    public String buildUri(RequestParams params, HttpRequest httpRequest) throws Throwable {
        if (FirstHand.isHost()) {
            return FirstHand.getInstance().url_host + httpRequest.path() + params.getUri();
        }
        return httpRequest.host() + httpRequest.path() + params.getUri();
    }

    @Override
    public String buildCacheKey(RequestParams params, String[] cacheKeys) {
        return null;
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() throws Throwable {
        return null;
    }

    @Override
    public void buildParams(RequestParams params) throws Throwable {
        FirstHand hand = FirstHand.getInstance();
        if (FirstHand.isHand()) {
            params.addParameter("app_id", hand.app_id);
        } else {
            params.addParameter("type", ApiConstants.COMMON_DEV_TYPE);
        }
        params.addParameter("dev_id", Build.FINGERPRINT);
        params.addParameter("ver_code", String.valueOf(AppUtils.getAppVersionCode(App.getInstance())));
        params.addParameter("tick", String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void buildSign(RequestParams params, String[] signs) throws Throwable {
        List<KeyValue> list = params.getBodyParams();
        StringBuilder builder = new StringBuilder();
        if (FirstHand.isHand()) {
            builder.append(FirstHand.getInstance().private_key);
        } else {
            builder.append(ApiConstants.COMMON_PUBLIC_KEY);
        }
        int size = list.size();
        if (size > 4) {
            List<KeyValue> subList = list.subList(0, size - 4);
            List<KeyValue> commonList = list.subList(size - 4, size);
            for (KeyValue kv : commonList) {
                builder.append(kv.getValueStr());
            }
            for (KeyValue kv : subList) {
                builder.append(kv.getValueStr());
            }
        } else {
            for (KeyValue kv : list) {
                builder.append(kv.getValueStr());
            }
        }

        params.addParameter("sign", Md5.toMd5(builder.toString()).toUpperCase());
    }

}
