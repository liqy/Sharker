package com.sharker.network;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sharker.models.data.ResponseData;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 14:25
 */

public class SharkerResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
            Logger.json(result);
            ResponseData data = fromJson(result, resultClass);
            if (data.isResponseOk()) {
                return data.data;
            } else {
                return resultClass.newInstance();
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
}
