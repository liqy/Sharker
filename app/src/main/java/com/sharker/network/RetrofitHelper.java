package com.sharker.network;

import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sharker.App;
import com.sharker.models.FirstHand;
import com.sharker.network.api.UserService;
import com.sharker.utils.CommonUtil;
import com.sharker.utils.Md5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * Retrofit帮助类
 */
public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static UserService getUserAPI() {
        return createApi(UserService.class, ApiConstants.USER_BASE_URL);
    }

    public static Map<String, String> createParams() {
        Map<String, String> params = new LinkedHashMap<>();
        return params;
    }

    /**
     * 获取请求参数
     * @param params
     * @return
     */
    public static Map<String, String> getParams(Map<String, String> params) {
        return getParams(params, "");
    }

    /**
     * 当使用登录接口时请使用此接口
     *
     * @param params
     * @param url
     * @return
     */
    public static Map<String, String> getParams(Map<String, String> params, String url) {
        if (FirstHand.isHand()) {
            params.put("app_id", FirstHand.getInstance().app_id);
        } else {
            params.put("type", ApiConstants.COMMON_DEV_TYPE);
        }
        params.put("dev_id", Build.FINGERPRINT);
        params.put("ver_code", String.valueOf(AppUtils.getAppVersionCode(App.getInstance())));
        params.put("tick", String.valueOf(System.currentTimeMillis()));
        buildSign(params, url);
        return params;
    }

    public static Map<String, String> buildSign(Map<String, String> params, String url) {
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
            String session = "";
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

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addInterceptor(new HostSelectionInterceptor())
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    private static class HostSelectionInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (FirstHand.isHost()) {
                String host = FirstHand.getInstance().url_host;
                request = request.newBuilder()
                        .url(host + request.url().url().getPath())
                        .build();
            }
            return chain.proceed(request);
        }
    }


    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(App.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(App.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
