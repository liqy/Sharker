package com.sharker.network;

import com.sharker.models.FirstHand;
import com.sharker.models.RequestData;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;

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
        RequestData data = new RequestData();
        FirstHand hand = FirstHand.getInstance();

        params.addParameter("dev_id", data.dev_id);
        params.addParameter("ver_code", data.ver_code);
        params.addParameter("tick", data.tick);

        if (FirstHand.isHand()) {
            params.addParameter("app_id", hand.app_id);
        } else {
            params.addParameter("type", data.type);
        }
        params.addParameter("sign", data.generateSign(params));
    }

    @Override
    public void buildSign(RequestParams params, String[] signs) throws Throwable {

    }
}
