package com.sharker.network.api;

import com.sharker.models.FirstHand;
import com.sharker.models.data.RequestData;
import com.sharker.models.data.ResponseData;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/6 21:46
 */

public interface UserService {

    @POST("/app/v1/first_hand")
    Observable<ResponseData<FirstHand>> firstHand(@Body RequestData requestData);
}
