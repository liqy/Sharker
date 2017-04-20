package com.sharker.network.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/14 11:14
 */

public interface HelloService {
    @GET("/helloworld.txt")
    Observable<String> helloworld();
}
