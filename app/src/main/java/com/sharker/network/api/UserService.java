package com.sharker.network.api;

import com.sharker.models.FirstHand;
import com.sharker.models.TopicDetail;
import com.sharker.models.data.RequestData;
import com.sharker.models.data.ResponseData;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/6 21:46
 */
public interface UserService {

    @FormUrlEncoded
    @POST("/app/v1/first_hand")
    Observable<ResponseData<FirstHand>> firstHand(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/v1/detail_topic")
    Observable<ResponseData<TopicDetail>> detailTopic(@FieldMap Map<String,String> params);
}
