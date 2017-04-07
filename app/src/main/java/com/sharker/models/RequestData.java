package com.sharker.models;

import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.sharker.App;
import com.sharker.network.ApiConstants;
import com.sharker.utils.Md5;

import org.xutils.http.RequestParams;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 10:01
 */

public class RequestData {
    public String type;
    public String dev_id;
    public String ver_code;
    public String tick;
    public String sign;

    public RequestData() {
        this.type = ApiConstants.COMMON_DEV_TYPE;
        this.dev_id = Build.FINGERPRINT;
        this.ver_code = String.valueOf(AppUtils.getAppVersionCode(App.getInstance()));
        this.tick = String.valueOf(System.currentTimeMillis());
    }

    public String generateSign(RequestParams params) {
        StringBuilder builder = new StringBuilder();
        if (FirstHand.isHand()) {
            builder.append(FirstHand.getInstance().private_key)
                    .append(FirstHand.getInstance().app_id);
        } else {
            builder.append(ApiConstants.COMMON_PUBLIC_KEY)
                    .append(this.type);
        }

        builder.append(this.dev_id)
                .append(this.ver_code)
                .append(this.tick);

        return Md5.toMd5(builder.toString()).toUpperCase();
    }

}
