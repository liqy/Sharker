package com.sharker.network;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 13:29
 */

@HttpRequest(
        host = ApiConstants.USER_BASE_URL,
        path = ApiConstants.USER_BASE_PATH,
        builder = SharkerParamsBuilder.class
)
public class SharkerParams extends RequestParams{

    public SharkerParams() {
    }

    public SharkerParams(String uri) {
        super(uri);
    }

}
