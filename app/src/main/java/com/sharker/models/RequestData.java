package com.sharker.models;

import android.os.Build;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.sharker.App;
import com.sharker.network.ApiConstants;

import java.util.Calendar;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/7 10:01
 */

public abstract class RequestData {
    public String type;
    public String dev_id;
    public String ver_code;
    public String tick;
    public String sign;

    private RequestData() {
        this.type = ApiConstants.COMMON_DEV_TYPE;
        this.dev_id = Build.FINGERPRINT;
        this.ver_code = String.valueOf(AppUtils.getAppVersionCode(App.getInstance()));
        this.tick = String.valueOf(Calendar.getInstance().getTime().getTime());
//        this.tick = "" + System.currentTimeMillis();
        this.sign = generateSign();//相关计算规则生成

    }

    public String generateSign() {
        return EncryptUtils.encryptMD5ToString("");
    }

}
