package com.sharker.network.volley;

import android.os.Build;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sharker.App;
import com.sharker.models.FirstHand;
import com.sharker.models.data.ResponseData;
import com.sharker.network.ApiConstants;
import com.sharker.utils.Md5;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/10 10:55
 */

public class SharkerRequest<T> extends Request<T> {

    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Map<String, String> params;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public SharkerRequest(String url, int method, Class<T> clazz, Map<String, String> params,
                          Map<String, String> headers,
                          Response.Listener<T> listener,
                          Response.ErrorListener errorListener) {
        super(method, FirstHand.isHost() ? FirstHand.getInstance().url_host + ApiConstants.USER_BASE_PATH + url : ApiConstants.USER_BASE_URL + ApiConstants.USER_BASE_PATH + url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.params = params;
    }


    public SharkerRequest(String url, Class<T> clazz, Map<String, String> params,
                          Response.Listener<T> listener,
                          Response.ErrorListener errorListener) {
        this(url, Method.POST, clazz, params, null, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headerMap = new HashMap<>(headers != null ? headers : super.getHeaders());
        headerMap.put("User-Agent", "android-open-project-analysis/1.0");
        return headerMap;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (FirstHand.isHand()) {
            params.put("app_id", FirstHand.getInstance().app_id);
        } else {
            params.put("type", ApiConstants.COMMON_DEV_TYPE);
        }
        params.put("dev_id", Build.FINGERPRINT);
        params.put("ver_code", String.valueOf(AppUtils.getAppVersionCode(App.getInstance())));
        params.put("tick", String.valueOf(System.currentTimeMillis()));
        buildSign(params, getUrl());
        return params;
    }

    public Map<String, String> buildSign(Map<String, String> params, String url) {
        List<String> list = new ArrayList<>(params.values());

        StringBuilder builder = new StringBuilder();
        if (FirstHand.isHand()) {
            builder.append(FirstHand.getInstance().private_key);
        } else {
            builder.append(ApiConstants.COMMON_PUBLIC_KEY);
        }

        int size = list.size();

        //TODO 登录特出处理
        if (url.contains("user_login")) {
            String number = params.get("number");
            builder.append(FirstHand.getInstance().app_id).append(number);
            List<String> loginList = list.subList(2, size);
            for (String kv : loginList) {
                builder.append(kv);
            }
        } else {
            //TODO 登录之后的逻辑需要重新处理
            String session = FirstHand.getInstance().session;
            if (TextUtils.isEmpty(session)) {
                if (size > 4) {
                    List<String> subList = list.subList(0, size - 4);
                    List<String> commonList = list.subList(size - 4, size);
                    for (String kv : commonList) {
                        builder.append(kv);
                    }
                    for (String kv : subList) {
                        builder.append(kv);
                    }
                } else {
                    for (String kv : list) {
                        builder.append(kv);
                    }
                }
            } else {
                //TODO 登录之后的签名处理
            }
        }

        //对参数的顺序有要求
        params.put("sign", Md5.toMd5(builder.toString()).toUpperCase());

        return params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Logger.json(json);
            ResponseData<T> data = fromJson(json, clazz);
            if (data.isResponseOk()) {
                return Response.success(
                        data.data,
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success(
                        clazz.newInstance(),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (InstantiationException e) {
            return Response.error(new ParseError(e));
        } catch (IllegalAccessException e) {
            return Response.error(new ParseError(e));
        }
    }

    public ResponseData fromJson(String json, Class clazz) {
        //TODO 做成一个单例 Gson
        Gson gson = new Gson();
        Type objectType = type(ResponseData.class, clazz);
        return gson.fromJson(json, objectType);
    }

    ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }
}
