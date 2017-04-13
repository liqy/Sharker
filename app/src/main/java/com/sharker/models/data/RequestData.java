package com.sharker.models.data;

import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.sharker.App;
import com.sharker.network.ApiConstants;

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
    public String session;

    public RequestData() {
        this.type = ApiConstants.COMMON_DEV_TYPE;
        this.dev_id = Build.FINGERPRINT;
        this.ver_code = String.valueOf(AppUtils.getAppVersionCode(App.getInstance()));
        this.tick = String.valueOf(System.currentTimeMillis());
    }

}
